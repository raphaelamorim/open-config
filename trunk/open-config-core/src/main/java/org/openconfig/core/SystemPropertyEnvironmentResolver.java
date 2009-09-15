package org.openconfig.core;

import org.openconfig.Environment;

/**
 * @author Richard L. Burton III
 */
public class SystemPropertyEnvironmentResolver implements EnvironmentResolver {

    public static final String DEFAULT_SYSTEM_PROPERTY_ENVIRONMENT_VARIABLE = "environment";

    private String variable = DEFAULT_SYSTEM_PROPERTY_ENVIRONMENT_VARIABLE;

    public Environment resolve(OpenConfigContext context) {
        return new Environment(System.getProperty(DEFAULT_SYSTEM_PROPERTY_ENVIRONMENT_VARIABLE));
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

}
