package org.openconfig.ioc;

import org.junit.Before;
import org.junit.Test;
import org.openconfig.factory.NoConfigurationFileFoundException;
import org.openconfig.factory.impl.InternalConfigurationLocator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class InternalConfigurationLocatorTest {

    private InternalConfigurationLocator locator;

    @Test
    public void verifySuccessfulLocation() throws NoConfigurationFileFoundException {
        assertNotNull(locator.locate());
    }

    @Test
    public void verifyFailedLocation()  {
        try {
            locator.setLocation("/not/valid");
            locator.locate();
            fail("Expected NoConfigurationFileFoundException");
        }catch(NoConfigurationFileFoundException nce){
            assertNotNull(nce);
        }
    }

    @Before
    public void setup(){
        locator = new InternalConfigurationLocator();
    }
}
