package org.openconfig.junit;

import junit.framework.TestCase;
import org.openconfig.Environment;
import org.openconfig.core.SystemPropertyEnvironmentResolver;

/**
 * Acts as a base for unit tests that depend on open-config being used in local mode.
 *
 * @author Dushyanth (Dee) Inguva
 */
public abstract class LocalTestCase extends TestCase {

    @Override
    protected final void setUp() throws Exception {
        System.setProperty(SystemPropertyEnvironmentResolver.DEFAULT_SYSTEM_PROPERTY_ENVIRONMENT_VARIABLE, Environment.LOCAL_ENVIRONMENT);
        doSetUp();
    }

    protected void doSetUp() throws Exception {
    }

    @Override
    protected final void tearDown() throws Exception {
        System.setProperty(SystemPropertyEnvironmentResolver.DEFAULT_SYSTEM_PROPERTY_ENVIRONMENT_VARIABLE, "");
        doTearDown();
    }

    protected void doTearDown() throws Exception {

    }

}
