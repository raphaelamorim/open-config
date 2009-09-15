package org.openconfig;

import org.openconfig.factory.ConfigurationFactoryBuilder;
import org.openconfig.factory.ConfiguratorFactory;
import org.openconfig.junit.LocalTestCase;

/**
 * @author Richard L. Burton III
 */
public class ConfiguratorTestCase extends LocalTestCase {

    private ConfiguratorFactory factory;

    private MyConfigurator configurator;

    @Override
    public void doSetUp() {
        factory = new ConfigurationFactoryBuilder().build();
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
