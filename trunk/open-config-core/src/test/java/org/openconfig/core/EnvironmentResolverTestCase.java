package org.openconfig.core;

import junit.framework.TestCase;
import org.openconfig.Environment;
import static org.openconfig.Environment.LOCAL;
import static org.openconfig.Environment.LOCAL_ENVIRONMENT;
import static org.openconfig.core.SystemPropertyEnvironmentResolver.DEFAULT_SYSTEM_PROPERTY_ENVIRONMENT_VARIABLE;

import static java.util.Collections.EMPTY_MAP;

/**
 * @author Richard L. Burton III
 */
public class EnvironmentResolverTestCase extends TestCase {


    public void testBasic() {
        System.setProperty(DEFAULT_SYSTEM_PROPERTY_ENVIRONMENT_VARIABLE, LOCAL_ENVIRONMENT);
        OpenConfigContext context = new BasicOpenConfigContext(EMPTY_MAP);
        EnvironmentResolver environmentResolver = new SystemPropertyEnvironmentResolver();
        Environment environment = environmentResolver.resolve(context);
        assertEquals(LOCAL, environment);
        System.setProperty(DEFAULT_SYSTEM_PROPERTY_ENVIRONMENT_VARIABLE, "");
    }

}
