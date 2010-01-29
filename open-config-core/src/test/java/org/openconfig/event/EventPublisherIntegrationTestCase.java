package org.openconfig.event;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.openconfig.factory.ConfigurationFactoryBuilder;
import org.openconfig.factory.ConfiguratorFactory;
import org.openconfig.factory.NoOpConfiguratorLocator;
import org.openconfig.junit.LocalTestCase;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Properties;

/**
 * @author Dushyanth (Dee) Inguva
 */
public class EventPublisherIntegrationTestCase extends LocalTestCase {
    private ConfiguratorFactory factory;

    @Test
    public void testEventFiredOnReload() throws Exception {
        EventListener eventListener = createMock(EventListener.class);

        eventListener.onEvent((ChangeStateEvent) anyObject());
        replay(eventListener);

        StubConfigurator configurator = factory.newInstance(StubConfigurator.class, eventListener);

        assertEquals("Bond", configurator.getName());
        assertEquals(45, configurator.getAge());
        URL url = getClass().getClassLoader().getResource("StubConfigurator.properties");
        File file = new File(url.toURI());

        Properties properties = new Properties();
        String newName = "James";
        int newAge = 46;
        properties.setProperty("name", newName);
        properties.setProperty("age", String.valueOf(newAge));

        PrintWriter printWriter = new PrintWriter(new FileWriter(file));
        properties.list(printWriter);
        printWriter.close();

        assertEquals(newName, configurator.getName());
        assertEquals(newAge, configurator.getAge());
        verify(eventListener);
    }

    @Before
    public void doSetUp() {
        ConfigurationFactoryBuilder configurationFactoryBuilder = new ConfigurationFactoryBuilder();
        configurationFactoryBuilder.setConfigurationLocator(new NoOpConfiguratorLocator());
        factory = configurationFactoryBuilder.build();
    }
}
