package org.openconfig.ioc;

import static org.apache.log4j.Logger.getLogger;
import static org.openconfig.util.Assert.notNull;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openconfig.ioc.config.MergingOpenConfigConfiguration;
import org.openconfig.ioc.config.OpenConfigConfiguration;
import org.openconfig.ioc.config.PropertiesOpenConfigConfiguration;

/**
 * @author Richard L. Burton III
 * @author Dushyanth (Dee) Inguva
 */
public class ConfigurationFileProcessor {

    private static final Logger LOGGER = getLogger(ConfigurationFileProcessor.class);

    private final Map<String, OpenConfigConfiguration> configurationManagers = new LinkedHashMap<String, OpenConfigConfiguration>();

    private final OpenConfigConfiguration defaultOpenConfigConfiguration = new PropertiesOpenConfigConfiguration();

    protected URL internalConfigurationFile;

    public ConfigurationFileProcessor(URL internalConfigurationFile) {
        this.internalConfigurationFile = internalConfigurationFile;
    }

    /**
     * Locates and initializes the user supplied configuration to use. This can be a properties file or an xml file with the name
     * "open-config.properties" or  "open-config.xml" and be accessible via the classpath.
     */
    public OpenConfigConfiguration process(URL customConfigurationFile) {
        notNull(internalConfigurationFile, "The internal configuration file (internalConfigurationFile) was not set!");
        defaultOpenConfigConfiguration.process(internalConfigurationFile);

        if(customConfigurationFile != null){
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("Processing custom configuration file " + customConfigurationFile.toString());
            }
            OpenConfigConfiguration customConfiguration = findOpenConfigConfiguration(customConfigurationFile);
            if (customConfiguration != null) {
                LOGGER.info("Merging custom configuration file [" + customConfiguration + "] with the internal configuration file.");
                return new MergingOpenConfigConfiguration(customConfiguration, defaultOpenConfigConfiguration);
            }
        }
        return defaultOpenConfigConfiguration;
    }


    protected OpenConfigConfiguration findOpenConfigConfiguration(URL file) {
        String[] parts = file.getPath().split("\\.");
        String fileExtension = parts[parts.length - 1];
        OpenConfigConfiguration ooc = configurationManagers.get(fileExtension);
        if (ooc == null) {
            throw new IllegalArgumentException("Unknown configuration file: " + file + ". Supported file extensions are: " + configurationManagers.keySet());
        }
        ooc.process(file);
        return ooc;
    }

    public void addOpenConfigConfiguration(String extension, OpenConfigConfiguration occ) {
        this.configurationManagers.put(extension, occ);
    }
}
