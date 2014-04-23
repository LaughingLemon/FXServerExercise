package laughing.lemon.foreign.exchange;
//Created by Shaun

import org.apache.commons.math3.distribution.NormalDistribution;

import static java.lang.Math.abs;

//uses normal distribution to generate random exchange rate values
public class ExchangeRateGenerator {
    private String fromCurrency = "";
    private String toCurrency = "";
    private RandomGenerator randomGenerator;
    private NormalDistribution normalDistribution;

    public ExchangeRateGenerator(RandomGenerator randomGenerator,
                                 String fromCurrency,
                                 String toCurrency,
                                 double maxRate,
                                 double minRate) {
        this.randomGenerator = randomGenerator;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        //calculate mean and standard deviation for min and max values
        double mean = (abs(maxRate) + abs(minRate)) / 2.0;
        double sd = (abs(maxRate) - abs(minRate)) / 4.0;
        //and set up the  normal distribution object
        this.normalDistribution = new NormalDistribution(mean, sd);
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public double generateRate() {
        //given a random value between 0 and 1, return a normal value
        return normalDistribution.inverseCumulativeProbability(randomGenerator.random());
    }

}
