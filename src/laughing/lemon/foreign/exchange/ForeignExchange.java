package laughing.lemon.foreign.exchange;
//Created by Shaun

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ForeignExchange {
    //used for formatting the exchange rate values to strings
    DecimalFormat decimalFormat = new DecimalFormat("0.0####");
    private List<ExchangeRate> exchangeRates = new ArrayList<ExchangeRate>();
    private List<ExchangeRateGenerator> exchangeRateGenerators = new ArrayList<ExchangeRateGenerator>();
    private RandomGenerator randomGenerator;

    public ForeignExchange(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
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

    @Override
    public String toString() {
        String returnValue = "";
        for(ExchangeRate rate : exchangeRates) {
            returnValue += rate.getFromCurrency() + "," +
                    rate.getToCurrency() + "," +
                    decimalFormat.format(rate.getCurrentRate());
            returnValue += '\t';
            returnValue += rate.getToCurrency() + "," +
                    rate.getFromCurrency() + "," +
                    decimalFormat.format(rate.getInverseCurrentRate());
            returnValue += '\t';
        }
        return returnValue;
    }

    public static void main(String[] args) {
        ForeignExchange foreignExchange = new ForeignExchange(new RandomGeneratorImpl());
        System.out.println(foreignExchange);
        foreignExchange.changeAnyExchangeRate();
        System.out.println(foreignExchange);
        foreignExchange.changeAnyExchangeRate();
        System.out.println(foreignExchange);
        foreignExchange.changeAnyExchangeRate();
        System.out.println(foreignExchange);
        foreignExchange.changeAnyExchangeRate();
        System.out.println(foreignExchange);
    }

}
