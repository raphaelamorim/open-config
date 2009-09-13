package org.openconfig.web;

import static org.openconfig.ObjectFactory.getInstance;
import org.openconfig.Environment;
import org.openconfig.core.EnvironmentResolver;
import org.openconfig.core.OpenConfigContext;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContext;

/**
 * * The bootstrap class for OpenConfig within a web environment.
 *
 * @author Richard L. Burton III
 */
public class OpenConfigBootstrapContextListener implements ServletContextListener {

    public static final String ENVIRONMENT_RESOLVER = "org.openconfig.environment_resolver";

    private Environment environment;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        String environmentResolverClass = servletContext.getInitParameter(ENVIRONMENT_RESOLVER);
        environmentResolverClass = cleanseString(environmentResolverClass);

        EnvironmentResolver environmentResolver;

        if (environmentResolverClass != null) {
            environmentResolver = getInstance().construct(environmentResolverClass);
        } else {
            environmentResolver = getInstance().getDefaultEnvironmentResolver();
        }

        // TODO: Remove this inner class...
        // TODO: How to pass around the active environment? CLEANLY
        environment = environmentResolver.resolve(new OpenConfigContext() {
            public String getParameter(String name) {
                return System.getProperty(name);
            }

            public String getEnvironmentProperty(String name) {
                return System.getProperty(name);
            }
        });
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    protected String cleanseString(String value) {
        if (value == null || value.trim().equals("")) {
            return null;
        } else {
            return value.trim();
        }
    }
}
