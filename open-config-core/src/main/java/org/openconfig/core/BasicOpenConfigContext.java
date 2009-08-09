package org.openconfig.core;

import java.util.Map;
import java.util.Collections;

/**
 * @author Richard L. Burton III
 */
public class BasicOpenConfigContext implements OpenConfigContext{

    private Map<String, String> parameters;

    public BasicOpenConfigContext(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public BasicOpenConfigContext() {
        this.parameters = Collections.EMPTY_MAP;
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public String getEnvironmentProperty(String name) {
        return System.getProperty(name);
    }

}
