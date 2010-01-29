package org.openconfig.factory.impl;

import org.openconfig.factory.ConfigurationLocator;
import org.openconfig.factory.NoConfigurationFileFoundException;

import java.net.URL;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class InternalConfigurationLocator implements ConfigurationLocator {

    /**
     * The configuration file used to customize how openconfig works.
     */
    private static final String DEFAULT_OPENCONFIG_CONFIG_FILE = "/org/openconfig/ioc/config/open-config.properties";

    private String location = DEFAULT_OPENCONFIG_CONFIG_FILE;

    public URL locate() throws NoConfigurationFileFoundException {
        URL url = getClass().getResource(location);
        if(url == null){
            throw new NoConfigurationFileFoundException("Failed to find to the internal configuration file " + DEFAULT_OPENCONFIG_CONFIG_FILE);
        }
        return url;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
