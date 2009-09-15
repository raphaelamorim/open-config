package org.openconfig.web;

import junit.framework.TestCase;
import org.openconfig.core.SystemPropertyEnvironmentResolver;
import org.springframework.mock.web.MockServletContext;

import javax.servlet.ServletContextEvent;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class OpenConfigBootstrapContextListenerTest extends TestCase {

    private MockServletContext servletContext;
    private ServletContextEvent servletContextEvent;

    public void testWithoutEnvironmentResolver() {
        verifyTest();
    }

    public void testWithEnvironmentResolver() {
        servletContext.addInitParameter(OpenConfigBootstrapContextListener.ENVIRONMENT_RESOLVER, "org.openconfig.web.ConstantEnvironmentResolver");
        verifyTest();
    }

    private void verifyTest() {
        OpenConfigBootstrapContextListener ocbcl = new OpenConfigBootstrapContextListener();
        ocbcl.contextInitialized(servletContextEvent);
        assertNotNull(servletContext.getAttribute(WebConfiguratorFactoryUtils.CONFIGURATOR_FACTORY_KEY));
    }

    @Override
    protected void setUp() throws Exception {
        System.setProperty(SystemPropertyEnvironmentResolver.DEFAULT_SYSTEM_PROPERTY_ENVIRONMENT_VARIABLE, "local");
        servletContext = new MockServletContext();
        servletContextEvent = new ServletContextEvent(servletContext);
    }
}
