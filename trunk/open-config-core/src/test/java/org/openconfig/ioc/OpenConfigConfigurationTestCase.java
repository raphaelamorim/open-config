package org.openconfig.ioc;

import java.io.IOException;
import java.util.LinkedHashMap;

import junit.framework.TestCase;

import org.openconfig.ioc.config.OpenConfigConfiguration;
import org.openconfig.ioc.config.PropertiesOpenConfigConfiguration;

/**
 * @author Richard L. Burton III
 */
public class OpenConfigConfigurationTestCase extends TestCase {

    private LinkedHashMap<String, OpenConfigConfiguration> configurationManagers = new LinkedHashMap<String, OpenConfigConfiguration>();

    @Override
    protected void setUp() throws Exception {
        configurationManagers.put(ConfigurationLocator.XML_FILE, new PropertiesOpenConfigConfiguration());
    }

    public void testBasic() throws IOException {
        ConfigurationLocator locator = new ConfigurationLocator(configurationManagers);
        locator.locate();
    }
}
