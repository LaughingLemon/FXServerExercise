package laughing.lemon.foreign.exchange;

/**
 * Class that stores exchange rate information
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 20, 2014</pre>
 */
public class ExchangeRate {

    private String fromCurrency = "";
    private String toCurrency = "";
    private double currentRate = 1.0;

    public ExchangeRate(String fromCurrency, String toCurrency, double currentRate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.currentRate = currentRate;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public double getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(double currentRate) {
        this.currentRate = currentRate;
    }

    //creates the inverse rate
    public double getInverseCurrentRate() {
        //traps divide by zero, just in case...
        return (currentRate != 0.0 ? 1.0 / currentRate : 0.0);
    }
}
