package org.openconfig.server.transformer;

import org.openconfig.jmx.builder.CompositeDataBuilder;
import org.openconfig.server.domain.Application;
import org.openconfig.server.domain.Configuration;

import javax.management.openmbean.CompositeData;
import java.util.Map;

/**
 * @author Dushyanth (Dee) Inguva - SmartCode LLC
 */
public class OpenMBeanApplicationTransformer {

    private OpenMBeanConfigurationValueTransformer valueTransformer;

    public void setValueTransformer(OpenMBeanConfigurationValueTransformer valueTransformer) {
        this.valueTransformer = valueTransformer;
    }

    /**
     * Transforms an Application into a CompositeData for transfer across JMX.
     *
     * @param application
     * @return
     */
    public CompositeData transform(Application application) {

        CompositeDataBuilder compositeDataBuilder = new CompositeDataBuilder(application.getName(), application.getDescription());
        for (Map.Entry<String, Configuration> entry : application.getConfigurations().entrySet()) {

            // Just because OpenMBeans doesn't seem to let us create an empty CompositeData object.
            if (entry.getValue().getValues().size() > 0) {
                valueTransformer.transform(entry.getKey(), entry.getValue().getDescription(), entry.getValue().getValues(),
                        compositeDataBuilder);
            }
        }
        return compositeDataBuilder.build();
    }
}
