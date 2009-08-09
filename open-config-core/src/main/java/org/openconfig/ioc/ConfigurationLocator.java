package org.openconfig.ioc;

import org.openconfig.ioc.config.OpenConfigConfiguration;
import org.openconfig.ioc.config.AggregatorOpenConfigConfiguration;

import java.util.*;
import java.net.URL;

/**
 * @author Richard L. Burton III
 */
public class ConfigurationLocator {

    public static final String CONFIGURATION_NAME = "META-INF/open-config";

    public static final List<String> CONFIGURATION_EXTENTIONS = Collections.unmodifiableList(Arrays.asList("xml", "properties"));

    private List<OpenConfigConfiguration> configurationManagers;

    public ConfigurationLocator(List<OpenConfigConfiguration> configurationManagers) {
        this.configurationManagers = configurationManagers;
    }

    public void locate() {
        ClassLoader classLoader = getClass().getClassLoader();
        for (String extention : CONFIGURATION_EXTENTIONS) {
            try {
                Enumeration<URL> configurations = classLoader.getResources(CONFIGURATION_NAME + '.' + extention);
                while (configurations.hasMoreElements()) {
                    URL url = configurations.nextElement();
                    String file = url.getFile();
                    process(file);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void process(String file) throws Exception {
        for (OpenConfigConfiguration ooc : configurationManagers) {
            if (ooc.accepts(file)) {
                ooc.process(file);
            }
        }
    }

    public OpenConfigConfiguration getOpenConfigConfiguration() {
        return new AggregatorOpenConfigConfiguration(configurationManagers);
    }
}
