import laughing.lemon.foreign.exchange.ExchangeRateGenerator;
import laughing.lemon.foreign.exchange.RandomGenerator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * ExchangeRateGenerator Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 20, 2014</pre>
 */
public class ExchangeRateGeneratorTest {

    /**
     * Method: generateRate()
     */
    @Test
    public void testGenerateRate() throws Exception {
        RandomGenerator randomGenerator = mock(RandomGenerator.class);
        ExchangeRateGenerator exchangeRateGenerator = new ExchangeRateGenerator(randomGenerator, "GBP", "EUR", 1.25, 0.75);
        when(randomGenerator.random()).thenReturn(0.5);
        assertEquals(1.0, exchangeRateGenerator.generateRate(), 0.0001);
    }

}