package org.openconfig.ioc;

import java.io.IOException;
import java.util.LinkedHashMap;

import junit.framework.TestCase;

import org.junit.Test;
import org.openconfig.core.BasicOpenConfigContext;
import org.openconfig.core.bean.StubLowercasePropertyNormalizer;
import org.openconfig.factory.ConfigurationLocator;
import org.openconfig.ioc.config.OpenConfigConfiguration;
import org.openconfig.ioc.config.PropertiesOpenConfigConfiguration;

/**
 * @author Richard L. Burton III
 * @author Dushyanth (Dee) Inguva
 */
public class OpenConfigConfigurationTestCase extends TestCase {

    private OpenConfigConfiguration openConfigConfiguration;

    @Override
    protected void setUp() throws Exception {
        openConfigConfiguration = new PropertiesOpenConfigConfiguration();
    }

   @Test
   public void testopenConfigConfigurationOverridability() {
       System.out.println("Test cases need to be implemented for this..");
//       openConfigConfiguration.process();
//       ConfigurationFileProcessor locator = new ConfigurationFileProcessor(configurationManagers);
//       locator.locate();
//       OpenConfigConfiguration configuration = locator.getOpenConfigConfiguration();
//       configuration.getClass("PropertyNormalizer").getClass().equals(StubLowercasePropertyNormalizer.class);
//       configuration.getClass("OpenConfigContext").getClass().equals(BasicOpenConfigContext.class);
   }
}
