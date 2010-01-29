package org.openconfig.junit;

import junit.framework.TestCase;
import org.openconfig.Environment;
import org.openconfig.core.SystemPropertyEnvironmentResolver;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

/**
 * Acts as a base for unit tests that depend on open-config being used in local mode.
 *
 * @author Dushyanth (Dee) Inguva
 */
public abstract class LocalTestCase {

    @Before
    public final void before(){
        System.setProperty(SystemPropertyEnvironmentResolver.ENVIRONMENT_VARIABLE, Environment.LOCAL_ENVIRONMENT);
    }

     @After
    public final void after() {
        System.setProperty(SystemPropertyEnvironmentResolver.ENVIRONMENT_VARIABLE, "");
    }
}
