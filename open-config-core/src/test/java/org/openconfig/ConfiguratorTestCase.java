package org.openconfig;

import static org.openconfig.ObjectFactory.getInstance;
import junit.framework.TestCase;

import static  org.openconfig.ioc.OpenConfigModule.OPEN_CONFIG_DEVELOPMENT_MODE;

import org.openconfig.factory.ConfiguratorFactory;

/**
 * @author Richard L. Burton III
 */
public class ConfiguratorTestCase extends TestCase {

    static {
        // TODO remove this hack
       System.setProperty(OPEN_CONFIG_DEVELOPMENT_MODE, "true");
    }

    private ConfiguratorFactory factory = getInstance().newConfiguratorFactory();

    private MyConfigurator configurator;

    public void testNamingConvention() throws Exception {
        configurator = factory.newInstance(MyConfigurator.class);
        assertEquals("Bond", configurator.getName());
        assertEquals(45, configurator.getAge());
        assertEquals(46, configurator.getAge2());
        assertEquals("SpringBreakAccident", configurator.getPerson().getChild().getName());
        assertEquals("James Bond", configurator.getPerson().getName());
    }

    public void ztestNoAlias() throws Exception{
        configurator = factory.newInstance(MyConfigurator.class, false);
        assertEquals("Bond", configurator.getName());
        assertEquals(45, configurator.getAge());
        assertEquals("age2", configurator.getAge2());
        assertEquals("person.child.name", configurator.getPerson().getChild().getName());
        assertEquals("person.name", configurator.getPerson().getName());
    }


}
