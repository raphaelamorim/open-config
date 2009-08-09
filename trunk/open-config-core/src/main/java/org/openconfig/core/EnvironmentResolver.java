package org.openconfig.core;

import org.openconfig.Environment;

/**
 * Defines the contract for resolving the active environment OpenConfig
 * is running within. Making this feature pluggable is very important since
 * there are too many ways of handling this.
 *
 * @author Richard L. Burton III
 */
public interface EnvironmentResolver {

    Environment resolve(OpenConfigContext context);

}
