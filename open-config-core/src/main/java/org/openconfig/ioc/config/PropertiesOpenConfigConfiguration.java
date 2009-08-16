package org.openconfig.ioc.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Richard L. Burton III
 */
public class PropertiesOpenConfigConfiguration extends AbstractOpenConfigConfiguration {

    public boolean accepts(String file) {
        return file.endsWith(".properties");
    }

    @SuppressWarnings("unchecked")
	public void process(String file) {
        Properties properties = new Properties();
        try {
			InputStream stream = getClass().getClassLoader().getResourceAsStream(file);
			if(stream == null) {
				throw new RuntimeException("Could not load the file: "+file + " from the classpath");
			}
			properties.load(stream);
		}catch (IOException e) {
			throw new RuntimeException("Could not load file: "+file, e);
		}
        Set set = properties.entrySet();
        for (Object entryObject : set) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) entryObject;
            addExtentionPoint(entry.getKey(), entry.getValue());
        }
    }

}
