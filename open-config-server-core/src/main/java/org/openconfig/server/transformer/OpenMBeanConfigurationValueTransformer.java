package org.openconfig.server.transformer;

import org.openconfig.jmx.builder.CompositeDataBuilder;
import org.openconfig.server.domain.ConfigurationValue;
import static org.openconfig.server.domain.ValueType.PARENT;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.SimpleType;
import static javax.management.openmbean.SimpleType.BOOLEAN;
import static javax.management.openmbean.SimpleType.STRING;
import static java.lang.String.format;
import java.util.Set;

/**
 * Transforms a ConfigurationValue into it's OpenMBean equivalent.
 *
 * @author Dushyanth (Dee) Inguva - SmartCode LLC
 */
public class OpenMBeanConfigurationValueTransformer {

    /**
     * Transforms a ConfigurationValue and adds it as a value to the parentDataBuilder.
     *
     * @param configurationValue
     * @param parentDataBuilder
     */
    public void transform(ConfigurationValue configurationValue, CompositeDataBuilder parentDataBuilder) {
        if (configurationValue.getValueType() == PARENT) {
            transform(configurationValue.getName(), configurationValue.getDescription(), configurationValue.getChildren(), parentDataBuilder);
            return;
        }

        transformSimpleType(configurationValue, parentDataBuilder);
    }

    /**
     * Transforms a bunch of configuration values and adds them to the parent.
     *
     * @param name
     * @param description
     * @param configurationValues
     * @param parentDataBuilder
     */
    public void transform(String name, String description, Set<ConfigurationValue> configurationValues, CompositeDataBuilder parentDataBuilder) {
        CompositeDataBuilder compositeDataBuilder = new CompositeDataBuilder(name, description);
        for (ConfigurationValue child : configurationValues) {
            transform(child, compositeDataBuilder);
        }
        CompositeData compositeData = compositeDataBuilder.build();
        parentDataBuilder.addAttribute(name, compositeData, compositeDataBuilder.getCompositeType());
    }

    /**
     * Transforms a simple type and adds it to the parent data builder.
     *
     * @param configurationValue
     * @param parentDataBuilder
     */
    private void transformSimpleType(ConfigurationValue configurationValue, CompositeDataBuilder parentDataBuilder) {
        Object value;
        SimpleType type;
        switch (configurationValue.getValueType()) {
            case NUMERIC:
                value = configurationValue.getNumericValue();
                type = STRING;
                break;
            case STRING:
                value = configurationValue.getStringValue();
                type = STRING;
                break;
            case BOOLEAN:
                value = configurationValue.isBooleanValue();
                type = BOOLEAN;
                break;
            case SECURE:
                value = configurationValue.getSecureValue();
                type = STRING;
                break;
            default:
                throw new UnsupportedOperationException(format("Unknown Configuration Value type: %s for configuration ",
                        configurationValue.getValueType(), configurationValue));

        }
        parentDataBuilder.addAttribute(configurationValue.getName(), value, type);
    }

}
