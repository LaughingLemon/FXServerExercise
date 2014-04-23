package laughing.lemon.foreign.exchange;

/**
 * wrapper around Math.random to allow mocking
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 20, 2014</pre>
 */
public class RandomGeneratorImpl implements RandomGenerator {
    public double random() {
        return Math.random();
    }

    //produces a random integer between 0 and maxValue, inclusive
    public int randomNumber(int maxValue) {
        return (int) (Math.random() * (maxValue + 1));
    }
}
