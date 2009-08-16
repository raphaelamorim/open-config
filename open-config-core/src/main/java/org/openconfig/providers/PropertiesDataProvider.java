package org.openconfig.providers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Richard L. Burton III
 */
public class PropertiesDataProvider extends AbstractDataProvider {

    private File propertiesFile;

    private long lastModified = -1;

    public void setPropertiesFile(File propertiesFile) {
        this.propertiesFile = propertiesFile;
        lastModified = propertiesFile.lastModified();
    }

    public Object getValue(String name) {
        reloadPropertiesIfNeeded();
        return super.getValue(name);
    }

    private void reloadPropertiesIfNeeded() {
        if(lastModified != propertiesFile.lastModified()){
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream(propertiesFile));
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File getPropertiesFile() {
        return propertiesFile;
    }

    public long getLastModified() {
        return lastModified;
    }


}
