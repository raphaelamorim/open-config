package org.openconfig.example.context;

import org.openconfig.ioc.OpenConfigModule;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class OpenConfigServletContext implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
       System.setProperty(OpenConfigModule.OPEN_CONFIG_DEVELOPMENT_MODE, "true");
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
