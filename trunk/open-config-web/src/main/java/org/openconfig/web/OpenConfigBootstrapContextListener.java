package org.openconfig.web;

import static org.openconfig.web.WebConfiguratorFactoryUtils.*;

import org.openconfig.factory.ConfigurationFactoryBuilder;
import org.openconfig.factory.ConfiguratorFactory;
import org.openconfig.core.EnvironmentResolver;
import org.openconfig.core.OpenConfigContext;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContext;

/**
 * The bootstrap class for OpenConfig within a web environment.
 *
 * <ol>
 *  <li>org.openconfig.environment_resolver - The fully qualified class name of a class that implements EnvironmentResolver</li>
 * </ol>
 * 
 * @author Richard L. Burton III
 */
@SuppressWarnings("unchecked")
public class OpenConfigBootstrapContextListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(OpenConfigBootstrapContextListener.class);

    public static final String ENVIRONMENT_RESOLVER = "org.openconfig.environment_resolver";

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext       = servletContextEvent.getServletContext();
        OpenConfigContext openConfigContext = new WebOpenConfigContext(servletContext);
        String environmentResolverClass     = openConfigContext.getParameter(ENVIRONMENT_RESOLVER);
        environmentResolverClass            = cleanseString(environmentResolverClass);
        ConfigurationFactoryBuilder cfb     = new ConfigurationFactoryBuilder()
                                              .setOpenConfigContext(openConfigContext);
        
        if (environmentResolverClass != null) {
            try {
                LOG.info("Using custom EnvironmentResolver '" + environmentResolverClass + "'.");
                cfb.setEnvironmentResolverClass((Class<EnvironmentResolver>) Class.forName(environmentResolverClass));
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("Unable to load the class '" + environmentResolverClass + "'.");
            }
        }

        ConfiguratorFactory cf = cfb.build();
        LOG.debug("Successfully created the ConfiguratorFactory.");
        servletContext.setAttribute(CONFIGURATOR_FACTORY_KEY, cf);
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
