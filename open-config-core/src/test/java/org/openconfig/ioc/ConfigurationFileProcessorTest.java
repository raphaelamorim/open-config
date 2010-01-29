package org.openconfig.ioc;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class ConfigurationFileProcessorTest {

    @Test
    public void verifySuccessfulTest() throws MalformedURLException {
        URL url = new URL("http://smartcode.com");
        ConfigurationFileProcessor configuration = new ConfigurationFileProcessor(url);
    }
}
