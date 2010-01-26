package org.openconfig.server.transformer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.openconfig.server.domain.Application;
import org.openconfig.server.domain.Configuration;
import static org.openconfig.server.domain.ConfigurationValue.newConfigurationValue;
import static org.openconfig.server.domain.ValueType.STRING;

import javax.management.openmbean.CompositeData;

/**
 * @author Dushyanth (Dee) Inguva - SmartCode LLC
 */
public class OpenMBeanApplicationTransformerTest {
    private OpenMBeanApplicationTransformer transformer = new OpenMBeanApplicationTransformer();
    private OpenMBeanConfigurationValueTransformer valueTransformer = new OpenMBeanConfigurationValueTransformer();

    @Test
    public void verifyTransform() {
        Application application = createApplication();
        Configuration config1 = createConfiguration();
        Configuration config2 = createConfiguration();
        config1.addConfigurationValue(newConfigurationValue("somekey", "somevalue", STRING));
        config2.addConfigurationValue(newConfigurationValue("somekey", "somevalue", STRING));
        application.addConfiguration("aConfig1", config1);
        application.addConfiguration("aConfig2", config2);

        CompositeData compositeData = transformer.transform(application);

        assertEquals("MOOO", compositeData.getCompositeType().getTypeName());
        assertEquals("a description", compositeData.getCompositeType().getDescription());

        assertEquals("somevalue", ((CompositeData) compositeData.get("aConfig1")).get("somekey"));
    }

    @Test
    public void verifyTransformSkipsEmptyConfiguration() {
        Application application = createApplication();
        Configuration config1 = createConfiguration();
        Configuration config2 = createConfiguration();
        config1.addConfigurationValue(newConfigurationValue("somekey", "somevalue", STRING));
        application.addConfiguration("aConfig1", config1);
        application.addConfiguration("aConfig2", config2);

        CompositeData compositeData = transformer.transform(application);

        assertTrue(compositeData.getCompositeType().keySet().size() == 1);
    }

    private Configuration createConfiguration() {
        Configuration config1 = new Configuration();
        config1.setDescription("-");
        return config1;
    }

    private Application createApplication() {
        Application application = new Application();
        application.setName("MOOO");
        application.setDescription("a description");
        return application;
    }

    @Before
    public void setup() {
        transformer.setValueTransformer(valueTransformer);
    }

}
