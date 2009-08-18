package org.openconfig.integration;

import junit.framework.TestCase;
import org.openconfig.factory.ConfiguratorFactory;
import org.openconfig.factory.impl.DefaultConfiguratorFactory;
import org.openconfig.Configurator;
import org.openconfig.event.EventListener;
import org.openconfig.event.ChangeStateEvent;

/**
 * @author Richard L. Burton III
 */
public class ConfigurationFactoryIntegrationTestCase extends TestCase {

    private ConfiguratorFactory configurationFactory = new DefaultConfiguratorFactory();

    public void testBasic() {
        Configurator configurator = configurationFactory.newInstance(new EventListener() {
            public void onEvent(ChangeStateEvent event) {
                System.out.println("ConfigurationFactoryIntegrationTestCase.onEvent");
            }
        });

        System.out.println("configurator.getValue(\"test\"); = " + configurator.getValue("test"));

    }
}
