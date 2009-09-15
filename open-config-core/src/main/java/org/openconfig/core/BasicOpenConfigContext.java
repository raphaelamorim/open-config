package org.openconfig.core;

import java.util.Map;
import java.util.Collections;

/**
 * TODO: Rename to SystemOpenConfigContext?
 * @author Richard L. Burton III
 */
public class BasicOpenConfigContext implements OpenConfigContext{

    private Map<String, String> parameters;

    public BasicOpenConfigContext() {
        this(System.getenv());
    }

    public BasicOpenConfigContext(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

}
