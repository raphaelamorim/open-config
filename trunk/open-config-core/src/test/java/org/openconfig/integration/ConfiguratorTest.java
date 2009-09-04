package org.openconfig.integration;

import junit.framework.TestCase;
import org.openconfig.factory.ConfiguratorFactory;
import org.openconfig.factory.impl.DefaultConfiguratorFactory;
import org.openconfig.integration.configurators.PrimitiveConfiguration;
import org.openconfig.integration.configurators.Person;
import static org.openconfig.ioc.OpenConfigModule.OPEN_CONFIG_DEVELOPMENT_MODE;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class ConfiguratorTest extends TestCase {

    private ConfiguratorFactory configuratorFactory;

    public void testConfiguratorInterfacedBackedByProperties(){
        System.setProperty(OPEN_CONFIG_DEVELOPMENT_MODE, "true");
        PrimitiveConfiguration configurator = configuratorFactory.newInstance(PrimitiveConfiguration.class);
        assertEquals("Richard", configurator.getName());
        assertEquals(30, configurator.getAge());
        assertEquals(1.01, configurator.getMoney());
        Person admin = configurator.getAdministrator();
        assertNotNull(admin);
        assertEquals("Richard", admin.getName());
        assertEquals(30, admin.getAge());
    }

    @Override
    protected void setUp() throws Exception {
        configuratorFactory = new DefaultConfiguratorFactory();
    }
}
