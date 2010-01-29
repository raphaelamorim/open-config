package org.openconfig.factory.impl;

import org.apache.log4j.Logger;
import org.openconfig.factory.ConfigurationLocator;
import org.openconfig.factory.NoConfigurationFileFoundException;

import java.net.URL;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class ClasspathConfigurationLocator implements ConfigurationLocator {

    private Logger LOGGER = Logger.getLogger(ClasspathConfigurationLocator.class);

    public static final String CONFIGURATION_NAME = "open-config";

    private static final String[] ACCEPTABLE_EXTENSIONS = {"properties", "xml"};

    public URL locate() throws NoConfigurationFileFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        for (String extension : ACCEPTABLE_EXTENSIONS) {
            String fileName = CONFIGURATION_NAME + '.' + extension;
            URL url = classLoader.getResource(fileName);
            if (url == null) {
                LOGGER.warn("No file found in class-path for " + fileName);
            } else {
                LOGGER.info("Using OpenConfig configuration file: " + fileName);
                return url;
            }
        }
        throw new NoConfigurationFileFoundException(CONFIGURATION_NAME, ACCEPTABLE_EXTENSIONS);
    }
    
}
