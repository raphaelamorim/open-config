package org.openconfig.web;

import org.openconfig.core.OpenConfigContext;

import javax.servlet.ServletContext;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class WebOpenConfigContext implements OpenConfigContext {

    private ServletContext servletContext;

    public WebOpenConfigContext(ServletContext context){
        this.servletContext= context;
    }
    public String getParameter(String name) {
        return servletContext.getInitParameter(name);
    }
    
}
