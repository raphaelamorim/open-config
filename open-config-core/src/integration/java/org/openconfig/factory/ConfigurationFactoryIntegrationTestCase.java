package org.openconfig.factory;

import static org.openconfig.ioc.OpenConfigModule.OPEN_CONFIG_DEVELOPMENT_FILE;
import org.openconfig.junit.LocalTestCase;
import org.openconfig.Configurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * TODO: Test all edge cases for asserts.
 *
 * @author Richard L. Burton III
 * @author Dushyanth (Dee) Inguva
 */
public class ConfigurationFactoryIntegrationTestCase extends LocalTestCase {

    private ConfiguratorFactory configurationFactory;

    @Test
    public void testBasic() {
        Configurator configurator = configurationFactory.newInstance();
        assertEquals("SmartCode", configurator.getValue("company"));
    }

    @Test
    public void testConfiguratorInterfacedBackedByProperties() {
        PrimitiveConfiguration configurator = configurationFactory.newInstance(PrimitiveConfiguration.class);
        assertEquals("Richard", configurator.getName());
        assertEquals(30, configurator.getAge());
        assertEquals(1.01, configurator.getMoney(), 0.00001);
        Person admin = configurator.getAdministrator();
        assertNotNull(admin);
        assertEquals("Richard", admin.getName());
        assertEquals(30, admin.getAge());
        assertEquals(DayOfWeek.Sun, admin.getDayOfWeek());
        assertEquals("iPhone", admin.getPhone().getModel());
    }

    @After
    public void tearDown() {
        System.setProperty(OPEN_CONFIG_DEVELOPMENT_FILE, "");
    }

    @Before
    public void setUp() {
        ConfigurationFactoryBuilder configurationFactoryBuilder = new ConfigurationFactoryBuilder();
        configurationFactoryBuilder.setConfigurationLocator(new NullConfiguratorLocator());
        configurationFactory = configurationFactoryBuilder.build();
    }
}
