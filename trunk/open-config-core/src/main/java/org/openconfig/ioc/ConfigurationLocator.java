package org.openconfig.ioc;

import static org.apache.log4j.Logger.getLogger;

import java.net.URL;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.openconfig.ioc.config.MergingOpenConfigConfiguration;
import org.openconfig.ioc.config.OpenConfigConfiguration;
import org.openconfig.ioc.config.PropertiesOpenConfigConfiguration;

/**
 * @author Richard L. Burton III
 * @author Dushyanth (Dee) Inguva
 */
public class ConfigurationLocator {

    private static final Logger LOGGER = getLogger(ConfigurationLocator.class);

    private static final String DEFAULT_OPENCONFIG_CONFIG_FILE = "org/openconfig/ioc/config/open-config.properties";
    public static final String CONFIGURATION_NAME = "open-config";

    public static final String XML_FILE = "xml";
    public static final String PROPERTIES_FILE = "properties";

    private final LinkedHashMap<String, OpenConfigConfiguration> configurationManagers;
    private OpenConfigConfiguration activeOpenConfigConfiguration;
    private final OpenConfigConfiguration defaultOpenConfigConfiguration;

    public ConfigurationLocator(LinkedHashMap<String, OpenConfigConfiguration> configurationManagers) {
    	this(configurationManagers, new PropertiesOpenConfigConfiguration());
    }

    public ConfigurationLocator(LinkedHashMap<String, OpenConfigConfiguration> configurationManagers, OpenConfigConfiguration defaultOpenConfigConfiguration) {
        this.configurationManagers = configurationManagers;
        this.defaultOpenConfigConfiguration = defaultOpenConfigConfiguration;
    }

    /**
     * Locates and initializes the user supplied configuration to use. This can be a properties file or an xml file with the name
     * "open-config.properties" or  "open-config.xml" and be accessible via the classpath. Then, it locates and initializes the default
     *  open config configuration with the file {@link #DEFAULT_OPENCONFIG_CONFIG_FILE}
     */
    public void locate() {
        ClassLoader classLoader = getClass().getClassLoader();

        for (String extension : configurationManagers.keySet()) {
            String fileName = CONFIGURATION_NAME + '.' + extension;
			URL url = classLoader.getResource(fileName);
            if(url == null) {
                LOGGER.debug("Could not find OpenConfig configuration file: "+fileName);
            }
            else {
                LOGGER.debug("Using OpenConfig configuration file: "+fileName);
                OpenConfigConfiguration config = process(fileName);
                LOGGER.debug("Loading OpenConfig default configuration file: "+DEFAULT_OPENCONFIG_CONFIG_FILE);
                defaultOpenConfigConfiguration.process(DEFAULT_OPENCONFIG_CONFIG_FILE);
                activeOpenConfigConfiguration = new MergingOpenConfigConfiguration(config, defaultOpenConfigConfiguration);
                return;
            }
        }
        throw new IllegalStateException("Could not find any OpenConfig configuration files: ");
    }

    protected OpenConfigConfiguration process(String file) {
    	String[] parts = file.split("\\.");
    	String fileExtension = parts[parts.length -1];
        OpenConfigConfiguration ooc = configurationManagers.get(fileExtension);
        if(ooc == null) {
        	throw new IllegalArgumentException("Unknown configuration file: "+file+ ". Supported file extensions are: "+configurationManagers.keySet());
        }
        ooc.process(file);
        return ooc;
    }

    public OpenConfigConfiguration getOpenConfigConfiguration() {
        return activeOpenConfigConfiguration;
	 }
}
