package org.openconfig.configurators;

import org.openconfig.MutableConfigurator;

import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Richard L. Burton III
 */
public class PropertiesConfigurator implements MutableConfigurator {

    private Properties properties = new Properties();

    public PropertiesConfigurator(Properties properties) {
        this.properties = properties;
    }

    public PropertiesConfigurator(String propertiesFile) throws IOException {
        this(new File(propertiesFile));
    }

    public PropertiesConfigurator(File propertiesFile) throws IOException {
        properties.load(new FileInputStream(propertiesFile));
    }

    public void setValue(String name, Object value) {
        properties.setProperty(name, String.valueOf(value));
    }

    public Object getValue(String key) {
        return properties.getProperty(key);
    }
}
