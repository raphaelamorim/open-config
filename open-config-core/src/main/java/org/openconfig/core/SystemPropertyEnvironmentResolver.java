package org.openconfig.core;

import org.openconfig.Environment;
import org.openconfig.util.Assert;

import static org.openconfig.Environment.LOCAL_ENVIRONMENT;

import static org.openconfig.util.Assert.hasLength;

/**
 * @author Richard L. Burton III
 */
public class SystemPropertyEnvironmentResolver implements EnvironmentResolver {

    public static final String ENVIRONMENT_VARIABLE = "environment";

    private String variable = ENVIRONMENT_VARIABLE;

    private static final String MISSING_ENVIRONMENT_VARIABLE = "No environment variable was found. Use the JVM -D%s=<environment name> e.g., -D%s=local for Local development.";

    public Environment resolve(OpenConfigContext context) {
        String environment = System.getProperty(ENVIRONMENT_VARIABLE);
        hasLength(environment, MISSING_ENVIRONMENT_VARIABLE, ENVIRONMENT_VARIABLE, LOCAL_ENVIRONMENT);
        return new Environment(environment);
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

}
