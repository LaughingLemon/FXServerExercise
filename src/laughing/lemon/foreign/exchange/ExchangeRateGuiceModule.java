package laughing.lemon.foreign.exchange;
/**
 * Guice dependency injection module
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 20, 2014</pre>
 */

import com.google.inject.AbstractModule;
import fdeng.assignment.common.EnvironmentSocketFactory;
import fdeng.assignment.common.EnvironmentSocketFactoryImpl;
import fdeng.assignment.server.EnvironmentSocketServer;
import fdeng.assignment.server.EnvironmentSocketServerInterface;

public class ExchangeRateGuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(RandomGenerator.class).to(RandomGeneratorImpl.class);
        //create the network server
        bind(EnvironmentSocketFactory.class).to(EnvironmentSocketFactoryImpl.class);
        bind(EnvironmentSocketServerInterface.class).to(EnvironmentSocketServer.class);
    }
}
