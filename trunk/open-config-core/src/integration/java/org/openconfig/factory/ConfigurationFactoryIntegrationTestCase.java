package org.openconfig.factory;

import org.openconfig.Configurator;
import org.openconfig.factory.ConfigurationFactoryBuilder;
import org.openconfig.factory.ConfiguratorFactory;
import static org.openconfig.ioc.OpenConfigModule.OPEN_CONFIG_DEVELOPMENT_FILE;

import org.openconfig.factory.DayOfWeek;
import org.openconfig.factory.Person;
import org.openconfig.factory.PrimitiveConfiguration;
import org.openconfig.junit.LocalTestCase;

/**
 * @author Richard L. Burton III
 * @author Dushyanth (Dee) Inguva
 */
public class ConfigurationFactoryIntegrationTestCase extends LocalTestCase {

    private ConfiguratorFactory configurationFactory;

    public void testBasic() {
        Configurator configurator = configurationFactory.newInstance();
        assertEquals("SmartCode", configurator.getValue("company"));
    }


    public void testConfiguratorInterfacedBackedByProperties(){
        PrimitiveConfiguration configurator = configurationFactory.newInstance(PrimitiveConfiguration.class);
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

    protected void doTearDown() {
        System.setProperty(OPEN_CONFIG_DEVELOPMENT_FILE, "");
    }

    protected void doSetUp() {
        configurationFactory = new ConfigurationFactoryBuilder().build();
    }

}
