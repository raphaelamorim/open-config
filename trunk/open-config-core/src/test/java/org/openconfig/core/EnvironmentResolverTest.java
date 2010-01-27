package org.openconfig.core;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Test;
import org.openconfig.Environment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.openconfig.Environment.LOCAL;
import static org.openconfig.Environment.LOCAL_ENVIRONMENT;
import static org.openconfig.core.SystemPropertyEnvironmentResolver.ENVIRONMENT_VARIABLE;

import static java.util.Collections.EMPTY_MAP;

/**
 * @author Richard L. Burton III
 */
public class EnvironmentResolverTest {

    @Test
    public void verifyEnvironmentResolution() {
        System.setProperty(ENVIRONMENT_VARIABLE, LOCAL_ENVIRONMENT);
        OpenConfigContext context = new BasicOpenConfigContext(EMPTY_MAP);
        EnvironmentResolver environmentResolver = new SystemPropertyEnvironmentResolver();
        Environment environment = environmentResolver.resolve(context);
        assertEquals(LOCAL, environment);
    }


    @Test
    public void verifyNoEnvironment() {
        try {
            OpenConfigContext context = new BasicOpenConfigContext(EMPTY_MAP);
            EnvironmentResolver environmentResolver = new SystemPropertyEnvironmentResolver();
            environmentResolver.resolve(context);
            fail("Expected IllegalArgumentException!");
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    public void verifyCustomEnvironment() {
        System.setProperty(ENVIRONMENT_VARIABLE, "config");
        OpenConfigContext context = new BasicOpenConfigContext(EMPTY_MAP);
        EnvironmentResolver environmentResolver = new SystemPropertyEnvironmentResolver();
        Environment environment = environmentResolver.resolve(context);
        assertEquals("config", environment.getName());
    }

    @After
    public void breakDown() {
        System.setProperty(ENVIRONMENT_VARIABLE, "");
    }

}
