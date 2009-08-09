package org.openconfig.ioc;

import junit.framework.TestCase;

import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

import org.openconfig.ioc.config.OpenConfigConfiguration;
import org.openconfig.ioc.config.PropertiesOpenConfigConfiguration;

/**
 * @author Richard L. Burton III
 */
public class OpenConfigConfigurationTestCase extends TestCase {

    private List<OpenConfigConfiguration> configurationManagers = new LinkedList<OpenConfigConfiguration>();

    @Override
    protected void setUp() throws Exception {
        configurationManagers.add(new PropertiesOpenConfigConfiguration());
    }

    public void testBasic() throws IOException {
        ConfigurationLocator locator = new ConfigurationLocator(configurationManagers);
        locator.locate();
    }
}
