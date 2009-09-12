package org.openconfig.integration;

import junit.framework.TestCase;
import org.openconfig.factory.ConfiguratorFactory;
import org.openconfig.factory.impl.DefaultConfiguratorFactory;
import org.openconfig.Configurator;
import org.openconfig.ioc.OpenConfigModule;
import static org.openconfig.ioc.OpenConfigModule.OPEN_CONFIG_DEVELOPMENT_MODE;
import static org.openconfig.ioc.OpenConfigModule.OPEN_CONFIG_DEVELOPMENT_FILE;
import org.openconfig.event.EventListener;
import org.openconfig.event.ChangeStateEvent;

/**
 * @author Richard L. Burton III
 */
public class ConfigurationFactoryIntegrationTestCase extends TestCase {

    static {
        System.setProperty(OPEN_CONFIG_DEVELOPMENT_MODE, "true");
        System.setProperty(OPEN_CONFIG_DEVELOPMENT_FILE, "MyConfigurator.properties");

    }
    private ConfiguratorFactory configurationFactory = new DefaultConfiguratorFactory();

    public void testBasic() {
        Configurator configurator = configurationFactory.newInstance(new EventListener() {
            public void onEvent(ChangeStateEvent event) {
                System.out.println("ConfigurationFactoryIntegrationTestCase.onEvent");
            }
        });

        System.out.println("configurator.getValue(\"name\"); = " + configurator.getValue("name"));

    }
}
