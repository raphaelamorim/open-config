package org.openconfig;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.openconfig.factory.ConfigurationFactoryBuilder;
import org.openconfig.factory.ConfiguratorFactory;
import org.openconfig.factory.NoOpConfiguratorLocator;
import org.openconfig.junit.LocalTestCase;

/**
 * @author Richard L. Burton III
 */
public class ConfiguratorTestCase extends LocalTestCase {

    private ConfiguratorFactory factory;

    private MyConfigurator configurator;

    @Before
    public void setUp() {
        ConfigurationFactoryBuilder configurationFactoryBuilder = new ConfigurationFactoryBuilder();
        configurationFactoryBuilder.setConfigurationLocator(new NoOpConfiguratorLocator());
        factory = configurationFactoryBuilder.build();
    }

    @Test
    public void testNamingConvention() throws Exception {
        configurator = factory.newInstance(MyConfigurator.class);
        assertEquals("Bond", configurator.getName());
        assertEquals(45, configurator.getAge());
        assertEquals(46, configurator.getAge2());
        assertEquals("Austin Powers", configurator.getPerson().getChild().getName());
        assertEquals("James Bond", configurator.getPerson().getName());
    }
}
