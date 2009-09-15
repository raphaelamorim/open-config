package org.openconfig.web;

import org.openconfig.core.EnvironmentResolver;
import org.openconfig.core.OpenConfigContext;
import org.openconfig.Environment;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class ConstantEnvironmentResolver implements EnvironmentResolver {
    public Environment resolve(OpenConfigContext context) {
        return Environment.LOCAL;
    }
}
