package org.openconfig.server;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openconfig.server.domain.Configuration;
import org.openconfig.server.domain.ConfigurationValue;
import static org.openconfig.server.domain.ConfigurationValue.newConfigurationValue;
import org.openconfig.server.domain.ValueType;
import org.openconfig.server.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

/**
 * @author Dushyanth (Dee) Inguva - SmartCode LLC
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring-test-config.xml")
public class ConfigurationServiceTest extends AbstractDatabaseIntegrationTest {

    @Autowired
    private ConfigurationService configurationService;

    @Test
    public void testSaveConfiguration() {
        Configuration configuration = createConfiguration();
        Set<ConfigurationValue> beforeSaving = configuration.getValues();
        configurationService.saveConfiguration(configuration);

        Configuration persistedConfiguration = configurationService.findConfiguration("MOOOO");
        assertEquals(configuration.getName(), persistedConfiguration.getName());

        Set<ConfigurationValue> afterSaving = persistedConfiguration.getValues();
        assertTrue(afterSaving.size() != 0);
        assertEquals(beforeSaving.size(), afterSaving.size());
        assertEquals(beforeSaving, afterSaving);
    }

    static Configuration createConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setName("MOOOO");
        ConfigurationValue integerValue = newConfigurationValue("numericValue", "55", ValueType.NUMERIC);
        ConfigurationValue doubleValue = newConfigurationValue("secureValue", "P@ssw0rd", ValueType.SECURE);
        ConfigurationValue stringValue = newConfigurationValue("stringValue", "test", ValueType.STRING);
        ConfigurationValue booleanValue = newConfigurationValue("booleanValue", true, ValueType.BOOLEAN);

        configuration.addConfigurationValue(integerValue);
        configuration.addConfigurationValue(doubleValue);
        configuration.addConfigurationValue(stringValue);
        configuration.addConfigurationValue(booleanValue);
        return configuration;
    }
}
