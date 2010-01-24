package org.openconfig.server.transformer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.openconfig.jmx.builder.CompositeDataBuilder;
import org.openconfig.server.domain.ConfigurationValue;
import static org.openconfig.server.domain.ConfigurationValue.newConfigurationValue;
import static org.openconfig.server.domain.ValueType.*;

import javax.management.openmbean.CompositeData;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Dushyanth (Dee) Inguva - SmartCode LLC
 */
public class OpenMBeanConfigurationValueTransformerTest {

    private OpenMBeanConfigurationValueTransformer transformer = new OpenMBeanConfigurationValueTransformer();

    @Test
    public void verifyTransformChildren() {
        String name = "MOOO";
        String description = "something longer";
        Set<ConfigurationValue> configurationValues = createConfigurationValues();
        CompositeDataBuilder compositeDataBuilder = new CompositeDataBuilder(name, description);

        transformer.transform(name, description, configurationValues, compositeDataBuilder);

        CompositeData compositeData = (CompositeData) compositeDataBuilder.build().get(name);
        assertEquals("testValue", compositeData.get("test1"));
        assertTrue((Boolean) compositeData.get("test2"));
        assertEquals("P@ssw0rd", compositeData.get("test3"));
        assertEquals("12", compositeData.get("test4"));
    }

    private Set<ConfigurationValue> createConfigurationValues() {
        Set<ConfigurationValue> configurationValues = new HashSet<ConfigurationValue>();
        configurationValues.add(newConfigurationValue("test1", "testValue", STRING));
        configurationValues.add(newConfigurationValue("test2", true, BOOLEAN));
        configurationValues.add(newConfigurationValue("test3", "P@ssw0rd", SECURE));
        configurationValues.add(newConfigurationValue("test4", "12", NUMERIC));

        return configurationValues;
    }

}
