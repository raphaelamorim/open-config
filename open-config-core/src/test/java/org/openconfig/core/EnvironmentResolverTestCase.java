package org.openconfig.core;

import junit.framework.TestCase;

import static java.util.Collections.EMPTY_MAP;

import org.openconfig.Environment;
import static org.openconfig.Environment.LOCAL;

/**
 * @author Richard L. Burton III
 */
public class EnvironmentResolverTestCase extends TestCase {

    public void testBasic() {
        OpenConfigContext context = new BasicOpenConfigContext(EMPTY_MAP);
        EnvironmentResolver environmentResolver = new SystemPropertyEnvironmentResolver();
        Environment environment = environmentResolver.resolve(context);
        assertEquals(LOCAL, environment);
    }
    
}
