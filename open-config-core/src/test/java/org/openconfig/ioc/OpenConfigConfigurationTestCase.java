package org.openconfig.ioc;

import java.io.IOException;
import java.util.LinkedHashMap;

import junit.framework.TestCase;

import org.junit.Test;
import org.openconfig.core.BasicOpenConfigContext;
import org.openconfig.core.bean.StubLowercasePropertyNormalizer;
import org.openconfig.ioc.config.OpenConfigConfiguration;
import org.openconfig.ioc.config.PropertiesOpenConfigConfiguration;

/**
 * @author Richard L. Burton III
 * @author Dushyanth (Dee) Inguva
 */
public class OpenConfigConfigurationTestCase extends TestCase {

    private LinkedHashMap<String, OpenConfigConfiguration> configurationManagers = new LinkedHashMap<String, OpenConfigConfiguration>();

    @Override
    protected void setUp() throws Exception {
        configurationManagers.put(ConfigurationLocator.PROPERTIES_FILE, new PropertiesOpenConfigConfiguration());
    }

    @Test
    public void testBasic() throws IOException {
        ConfigurationLocator locator = new ConfigurationLocator(configurationManagers);
        locator.locate();
    }
    
   @Test
   public void testopenConfigConfigurationOverridability() {
       ConfigurationLocator locator = new ConfigurationLocator(configurationManagers);
       locator.locate();
       OpenConfigConfiguration configuration = locator.getOpenConfigConfiguration();
       configuration.getClass("PropertyNormalizer").getClass().equals(StubLowercasePropertyNormalizer.class);
       configuration.getClass("OpenConfigContext").getClass().equals(BasicOpenConfigContext.class);
   }
}
