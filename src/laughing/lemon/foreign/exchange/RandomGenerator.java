package laughing.lemon.foreign.exchange;

/**
 * random number generator, which allows mocking
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 20, 2014</pre>
 */
public interface RandomGenerator {
    public double random();

    public int randomNumber(int maxValue);
}
