package org.openconfig.server.mbean;

import org.springframework.jmx.export.annotation.*;

import javax.management.openmbean.CompositeData;

/**
 * @author Dushyanth (Dee) Inguva - SmartCode LLC
 */
@ManagedResource(objectName = "org.openconfig:name=ConfigurationService", description = "Provides access to all OpenConfig Configurations configured on this server.")
public class ConfigurationService {

    private String[] configurationNames;
    //  private ConfigurationRepository configurationRepository;

    public ConfigurationService() {
        configurationNames = new String[]{"Twitter Web", "Twitter API", "Twitter Mobile", "Twitter HR"};
    }

    @ManagedAttribute(description = "Number of configurations defined on this openconfig server.")
    public int getNumberOfConfigurations() {
        return configurationNames.length;
    }

    @ManagedAttribute(description = "All the configuration names defined on this openconfig server.")
    public String[] getConfigurationNames() {
        return configurationNames;
    }

    /**
     * Gets the configuration with the given name.
     *
     * @throws IllegalArgumentException If the configuration is not present
     */
    @ManagedOperation(description = "Gets the configuration with the given name.")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "name", description = "The name of the configuration.")})
    public CompositeData getConfiguration(String name) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @ManagedOperation(description = "Determines if the configuration with the given name is present.")
    @ManagedOperationParameter(name = "name", description = "The name of the configuration.")
    public boolean isConfigurationPresent(String name) {
        return true;
    }

}
