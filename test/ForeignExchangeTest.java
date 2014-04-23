import laughing.lemon.foreign.exchange.ForeignExchange;
import laughing.lemon.foreign.exchange.RandomGenerator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * ForeignExchange Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 20, 2014</pre>
 */
public class ForeignExchangeTest {

    /**
     * Method: changeExchangeRate(String fromRate, String toRate)
     */
    @Test
    public void testChangeExchangeRate() throws Exception {
        RandomGenerator randomGenerator = mock(RandomGenerator.class);
        ForeignExchange foreignExchange = new ForeignExchange(randomGenerator);
        when(randomGenerator.random()).thenReturn(0.3);
        foreignExchange.changeExchangeRate("EUR", "USD");
        assertEquals(0.72930, foreignExchange.findExchangeRate("EUR", "USD").getCurrentRate(), 0.0001);
    }

    /**
     * Method: changeAnyExchangeRate()
     */
    @Test
    public void testChangeAnyExchangeRate() throws Exception {
        RandomGenerator randomGenerator = mock(RandomGenerator.class);
        ForeignExchange foreignExchange = new ForeignExchange(randomGenerator);
        when(randomGenerator.randomNumber(2)).thenReturn(1);
        when(randomGenerator.random()).thenReturn(0.7);
        foreignExchange.changeAnyExchangeRate();
        assertEquals(0.84221, foreignExchange.findExchangeRate("GBP", "EUR").getCurrentRate(), 0.0001);
    }

}
