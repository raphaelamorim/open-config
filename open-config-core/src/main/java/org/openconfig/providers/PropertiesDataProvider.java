package org.openconfig.providers;

import com.google.inject.name.Named;
import com.google.inject.Inject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Enumeration;

/**
 * @author Richard L. Burton III
 */
public class PropertiesDataProvider extends AbstractReloadableDataProvider {

    /**
     * The property configuration file backing the DataProvider.
     */
    private File propertiesFile;

    /**
     * The date/time when the underlaying properties file was updated.
     */
    private long lastModified = -1;

    /**
     * @param propertiesFile
     */
    @Inject
    @Named(value="PropertiesDataProvider.properties")
    public void setPropertiesFile(File propertiesFile) {
        this.propertiesFile = propertiesFile;
        lastModified = propertiesFile.lastModified();
    }

    public File getPropertiesFile() {
        return propertiesFile;
    }

    public long getLastModified() {
        return lastModified;
    }


    public boolean requiresReloading() {
        return lastModified != propertiesFile.lastModified();
    }

    public void reload() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propertiesFile));
            Enumeration<String> enumeration = (Enumeration<String>) properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String name = enumeration.nextElement();
                String value = properties.getProperty(name);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load the properties file " + propertiesFile);
        }
    }
}
