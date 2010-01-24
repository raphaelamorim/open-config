package org.openconfig.web;

import org.openconfig.factory.ConfiguratorFactory;

import javax.servlet.ServletContext;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public final class WebConfiguratorFactoryUtils {

    public static final String CONFIGURATOR_FACTORY_KEY = WebConfiguratorFactoryUtils.class.getPackage().getName();

    public static ConfiguratorFactory getConfiguratorFactory(ServletContext context) {
        ConfiguratorFactory instance = (ConfiguratorFactory) context.getAttribute(CONFIGURATOR_FACTORY_KEY);
        if(instance == null){
            throw new IllegalStateException("No ConfiguratorFactory was found in the ServletContext.\n" +
                    "*** Note: Register the OpenConfigBootstrapContextListener in the web.xml file ***.");
        }
        return instance;
    }
}
