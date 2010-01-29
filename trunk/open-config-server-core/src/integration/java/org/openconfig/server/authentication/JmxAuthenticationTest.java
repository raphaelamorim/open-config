package org.openconfig.server.authentication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openconfig.factory.ConfigurationFactoryBuilder;
import org.openconfig.factory.ConfiguratorFactory;
import org.openconfig.factory.NullConfiguratorLocator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/open-config-test-context.xml")
public class JmxAuthenticationTest {

    private ConfiguratorFactory configurationFactory;

    @Test
    public void verifyAuthentication() throws Exception {
        MessageDatabase messageDatabase = configurationFactory.newInstance(MessageDatabase.class);
        //System.out.println("messageDatabase.getPort() = " + messageDatabase.getPort());
        //System.out.println("messageDatabase.getServer() = " + messageDatabase.getServer());
    }

    @Before
    public void setup() {
        System.setProperty("environment", "local");
        System.setProperty("openconfig.app.config", "open-config.properties");
        ConfigurationFactoryBuilder configurationFactoryBuilder = new ConfigurationFactoryBuilder();
        configurationFactoryBuilder.setConfigurationLocator(new NullConfiguratorLocator());
        configurationFactory = configurationFactoryBuilder.build();
    }

    @After
    public void destroy() {
        System.setProperty("environment", "");
        System.setProperty("openconfig.app.config", "");
    }

}
