package org.openconfig.ioc.config;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Map;
import java.util.Set;

/**
 * @author Richard L. Burton III
 */
public class PropertiesOpenConfigConfiguration extends AbstractOpenConfigConfiguration {

    public boolean accepts(String file) {
        return file.endsWith(".properties");
    }

    public void process(String file) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream(file));
        Set set = properties.entrySet();
        for (Object entryObject : set) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) entryObject;
            addExtentionPoint(entry.getKey(), entry.getValue());
        }
    }

}
