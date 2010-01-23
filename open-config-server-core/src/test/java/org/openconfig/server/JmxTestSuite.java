package org.openconfig.server;

import integration.org.openconfig.server.authentication.JmxAuthenticationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    JmxAuthenticationTest.class
})
@ContextConfiguration(locations = "/spring-test-config.xml")
public class JmxTestSuite {

}
