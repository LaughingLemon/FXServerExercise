package laughing.lemon.foreign.exchange;
/**
 * main foreign exchange server class
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 20, 2014</pre>
 */

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import fdeng.assignment.common.EnvironmentSocketEvent;
import fdeng.assignment.common.EnvironmentSocketMessage;
import fdeng.assignment.server.EnvironmentSocketServerInterface;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ForeignExchange {
    //used for formatting the exchange rate values to strings
    DecimalFormat decimalFormat = new DecimalFormat("0.0####");
    private List<ExchangeRate> exchangeRates = new ArrayList<ExchangeRate>();
    private List<ExchangeRateGenerator> exchangeRateGenerators = new ArrayList<ExchangeRateGenerator>();
    private RandomGenerator randomGenerator;
    private EnvironmentSocketServerInterface socketServer;
    private int timeInterval = 30; //seconds

    @Inject
    public ForeignExchange(RandomGenerator randomGenerator,
                           EnvironmentSocketServerInterface socketServer) {
        this.randomGenerator = randomGenerator;
        this.socketServer = socketServer;
        this.socketServer.setMessageHandler(this.messageHandler);
        //sets up the initial exchange rates
        exchangeRates.add(new ExchangeRate("EUR", "GBP", 0.8371));
        exchangeRates.add(new ExchangeRate("USD", "EUR", 0.73335));
        exchangeRates.add(new ExchangeRate("GBP", "USD", 1.6351));

        //exchange rate generators use random values based on a normal distribution
        exchangeRateGenerators.add(new ExchangeRateGenerator(this.randomGenerator, "EUR", "GBP", 0.8566, 0.8176));
        exchangeRateGenerators.add(new ExchangeRateGenerator(this.randomGenerator, "USD", "EUR", 0.7488, 0.7179));
        exchangeRateGenerators.add(new ExchangeRateGenerator(this.randomGenerator, "GBP", "USD", 1.6798, 1.5904));
    }

    public ExchangeRate findExchangeRate(String fromRate, String toRate) {
        //find the exchange rate object for a pair of currencies
        for(ExchangeRate rate : exchangeRates) {
            //either from or to currency as one is the inverse of the other
            if((rate.getFromCurrency().equals(fromRate) && rate.getToCurrency().equals(toRate)) ||
                    (rate.getFromCurrency().equals(toRate) && rate.getToCurrency().equals(fromRate)))
                return rate;
        }
        //returns null if the currency pair isn't found
        return null;
    }

    private ExchangeRateGenerator findExchangeRateGenerator(String fromRate, String toRate) {
        //find the random generator object for a currency pair
        for(ExchangeRateGenerator generator : exchangeRateGenerators) {
            //either from or to currency
            if((generator.getFromCurrency().equals(fromRate) && generator.getToCurrency().equals(toRate)) ||
                    (generator.getFromCurrency().equals(toRate) && generator.getToCurrency().equals(fromRate)))
                return generator;
        }
        //return null if you can't find the pair
        return null;
    }

    public void changeExchangeRate(String fromRate, String toRate) {
        //get the exchange rate...
        ExchangeRate exchangeRate = findExchangeRate(fromRate, toRate);
        //... and it's generator
        ExchangeRateGenerator exchangeRateGenerator = findExchangeRateGenerator(fromRate, toRate);
        //...and, if you've found both successfully...
        if(exchangeRate != null && exchangeRateGenerator != null)
            //...use the generator to change the exchange rate
            exchangeRate.setCurrentRate(exchangeRateGenerator.generateRate());
    }

    public void changeAnyExchangeRate() {
        //randomly change any one of the exchange rate pairs
        switch(randomGenerator.randomNumber(2)) {
            case 0:
                changeExchangeRate("USD", "GBP");
                break;
            case 1:
                changeExchangeRate("GBP", "EUR");
                break;
            case 2:
                changeExchangeRate("EUR", "USD");
                break;
        }
    }

    public String exchangeRatesAsString() {
        String returnValue = "";
        //return all the exchange rates...
        for(ExchangeRate rate : exchangeRates) {
            returnValue += rate.getFromCurrency() + "," +
                    rate.getToCurrency() + "," +
                    decimalFormat.format(rate.getCurrentRate());
            returnValue += ';';
            //...and their inverses
            returnValue += rate.getToCurrency() + "," +
                    rate.getFromCurrency() + "," +
                    decimalFormat.format(rate.getInverseCurrentRate());
            returnValue += ';';
        }
        System.out.println(returnValue);
        return returnValue;
    }

    //handler object for messages from the clients
    private EnvironmentSocketEvent messageHandler = new EnvironmentSocketEvent() {
        public void messageReceived(EnvironmentSocketMessage e) {
            socketServer.sendMessage("DEAL");
        }
    };

    //clock to regularly update values
    Timer clock = new Timer();

    //interval is a minute
    private static final int SECOND_INTERVAL = 1000;
    //delay is zero
    private static final int CLOCK_DELAY = 0;

    //task run by the timer (effectively a thread)
    private class DataChange extends TimerTask {
        public void run() {
            //change one of the exchange rates
            changeAnyExchangeRate();
            //send the exchange rates to the clients
            socketServer.sendMessage(exchangeRatesAsString());
        }
    }

    private void slightSleep(long time) {
        //sleep for a short while
        try {
            Thread.sleep(time);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public void startClock() {
        //kicks off the network socket process
        socketServer.start();
        //kicks off the clock process
        clock.schedule(new DataChange(), CLOCK_DELAY, getTimeInterval() * SECOND_INTERVAL);
        //slight delay to allow the data to be read
        slightSleep(250);
    }

    public static void main(String[] args) {
        //create a guice dependency injector
        Injector injector = Guice.createInjector(new ExchangeRateGuiceModule());
        //get an instance of the foreign exchange object
        ForeignExchange foreignExchange = injector.getInstance(ForeignExchange.class);
        //start the timer
        foreignExchange.startClock();
    }

}
