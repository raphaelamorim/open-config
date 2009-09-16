package org.openconfig.integration;

import junit.framework.TestCase;
import org.openconfig.factory.ConfiguratorFactory;
import org.openconfig.factory.ConfigurationFactoryBuilder;
import org.openconfig.factory.impl.DefaultConfiguratorFactory;
import org.openconfig.integration.configurators.PrimitiveConfiguration;
import org.openconfig.integration.configurators.Person;
import org.openconfig.integration.configurators.DayOfWeek;
import org.openconfig.junit.LocalTestCase;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class ConfiguratorTest extends LocalTestCase {

    private ConfiguratorFactory configuratorFactory;

    public void testConfiguratorInterfacedBackedByProperties(){
        PrimitiveConfiguration configurator = configuratorFactory.newInstance(PrimitiveConfiguration.class);
        assertEquals("Richard", configurator.getName());
        assertEquals(30, configurator.getAge());
        assertEquals(1.01, configurator.getMoney());
        Person admin = configurator.getAdministrator();
        assertNotNull(admin);
        assertEquals("Richard", admin.getName());
        assertEquals(30, admin.getAge());
        assertEquals(DayOfWeek.Sun, admin.getDayOfWeek());
        assertEquals("iPhone", admin.getPhone().getModel());
    }

    @Override
    protected void doSetUp() throws Exception {
        configuratorFactory = new ConfigurationFactoryBuilder().build();
    }
}
