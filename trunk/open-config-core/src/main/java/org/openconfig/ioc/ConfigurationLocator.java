package org.openconfig.ioc;

import static org.apache.log4j.Logger.getLogger;

import java.net.URL;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.openconfig.ioc.config.OpenConfigConfiguration;

/**
 * @author Richard L. Burton III
 */
public class ConfigurationLocator {

    private static final Logger LOGGER = getLogger(ConfigurationLocator.class);

    public static final String CONFIGURATION_NAME = "META-INF/open-config";
    
    public static final String XML_FILE = "xml";
    public static final String PROPERTIES_FILE = "properties";

    private LinkedHashMap<String, OpenConfigConfiguration> configurationManagers;
    private OpenConfigConfiguration activeOpenConfigConfiguration;

    public ConfigurationLocator(LinkedHashMap<String, OpenConfigConfiguration> configurationManagers) {
        this.configurationManagers = configurationManagers;
    }

    public void locate() {
        ClassLoader classLoader = getClass().getClassLoader();
        
        for (String extension : configurationManagers.keySet()) {
            try {
                String fileName = CONFIGURATION_NAME + '.' + extension;
				URL url = classLoader.getResource(fileName);
                if(url == null) {
                    LOGGER.debug("Could not find OpenConfig configuration file: "+fileName);
                }
                else {
                    LOGGER.debug("Using OpenConfig Configuration file: "+fileName);
                    String file = url.getFile();
                    activeOpenConfigConfiguration = process(file);
                    return;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            throw new IllegalStateException("Could not find any OpenConfig configuration files: ");
        }
    }

    protected OpenConfigConfiguration process(String file) throws Exception {
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
