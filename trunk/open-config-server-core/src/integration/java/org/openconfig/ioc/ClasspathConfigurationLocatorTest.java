package org.openconfig.ioc;

import org.junit.Before;
import org.junit.Test;
import org.openconfig.factory.NoConfigurationFileFoundException;
import org.openconfig.factory.impl.ClasspathConfigurationLocator;

import java.net.URL;

import static org.junit.Assert.assertNotNull;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class ClasspathConfigurationLocatorTest {

    private ClasspathConfigurationLocator locator;

    @Test
    public void verifySuccessfulLocate() throws NoConfigurationFileFoundException {
        URL url = locator.locate();
        assertNotNull(url);
    }

    @Before
    public void setup(){
        locator = new ClasspathConfigurationLocator();
    }
}
