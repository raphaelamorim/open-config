package org.openconfig.integration;

import org.openconfig.Configurator;
import org.openconfig.factory.ConfigurationFactoryBuilder;
import org.openconfig.factory.ConfiguratorFactory;
import static org.openconfig.ioc.OpenConfigModule.OPEN_CONFIG_DEVELOPMENT_FILE;
import org.openconfig.junit.LocalTestCase;

/**
 * @author Richard L. Burton III
 * @author Dushyanth (Dee) Inguva
 */
public class ConfigurationFactoryIntegrationTestCase extends LocalTestCase {

    private ConfiguratorFactory configurationFactory;

    @Override
    protected void doSetUp() {
        configurationFactory = new ConfigurationFactoryBuilder().build();
    }

    public void testBasic() {
        Configurator configurator = configurationFactory.newInstance();

        assertEquals("SmartCode", configurator.getValue("company"));
    }

    @Override
    protected void doTearDown() {
        System.setProperty(OPEN_CONFIG_DEVELOPMENT_FILE, "");
    }
}
