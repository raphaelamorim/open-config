package org.openconfig.server.mbean;

import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.annotation.ManagedAttribute;

import java.util.Set;

/**
 * @author Dushyanth (Dee) Inguva - SmartCode LLC
 */
@ManagedResource(objectName="bean:name=configuration", description="Provides access to all OpenConfig Configurations configured on this server.")
public class Configuration
{

    private String[] configurationNames;

    public Configuration() {
        configurationNames = new String[]{"Twitter Web", "Twitter API", "Twitter Mobile", "Twitter HR"};
    }

    @ManagedAttribute(description="Number of configurations defined on this openconfig server.")
    public int getNumberOfConfigurations() {
        return configurationNames.length;
    }

    @ManagedAttribute(description="All the configuration names defined on this openconfig server.")
    public String[] getConfigurationNames() {
        return configurationNames;
    }
}
