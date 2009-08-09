package org.openconfig.core;

import org.openconfig.Environment;

/**
 * @author Richard L. Burton III
 */
public class SystemPropertyEnvironmentResolver implements EnvironmentResolver {

    private String variable = "environment";

    public Environment resolve(OpenConfigContext context) {
        return new Environment(context.getEnvironmentProperty(variable));
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

}
