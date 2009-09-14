package org.openconfig;

import junit.framework.TestCase;
import static org.openconfig.Environment.LOCAL_ENVIRONMENT;
import static org.openconfig.core.SystemPropertyEnvironmentResolver.DEFAULT_SYSTEM_PROPERTY_ENVIRONMENT_VARIABLE;
import org.openconfig.factory.ConfigurationFactoryBuilder;
import org.openconfig.factory.ConfiguratorFactory;
import static org.openconfig.ioc.OpenConfigModule.OPEN_CONFIG_DEVELOPMENT_MODE;

/**
 * @author Richard L. Burton III
 */
public class ConfiguratorTestCase extends TestCase {

    static {
        System.setProperty(OPEN_CONFIG_DEVELOPMENT_MODE, "true");
    }

    private ConfiguratorFactory factory;

    private MyConfigurator configurator;

    @Override
    public void setUp() {
        System.setProperty(DEFAULT_SYSTEM_PROPERTY_ENVIRONMENT_VARIABLE, LOCAL_ENVIRONMENT);
        factory = new ConfigurationFactoryBuilder().build();
    }

    @Override
    public void tearDown() {
        System.setProperty(DEFAULT_SYSTEM_PROPERTY_ENVIRONMENT_VARIABLE, "");
    }

    public void testNamingConvention() throws Exception {
        configurator = factory.newInstance(MyConfigurator.class);
        assertEquals("Bond", configurator.getName());
        assertEquals(45, configurator.getAge());
        assertEquals(46, configurator.getAge2());
        assertEquals("SpringBreakAccident", configurator.getPerson().getChild().getName());
        assertEquals("James Bond", configurator.getPerson().getName());
    }

    public void ztestNoAlias() throws Exception {
        configurator = factory.newInstance(MyConfigurator.class, false);
        assertEquals("Bond", configurator.getName());
        assertEquals(45, configurator.getAge());
        assertEquals("age2", configurator.getAge2());
        assertEquals("person.child.name", configurator.getPerson().getChild().getName());
        assertEquals("person.name", configurator.getPerson().getName());
    }


}
