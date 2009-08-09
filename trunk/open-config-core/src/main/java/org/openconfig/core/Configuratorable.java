package org.openconfig.core;

import org.openconfig.Configurator;

/**
 * Defines the contract for injecting the Configurator into a class.
 *
 * @author Richard L. Burton III
 */
public interface Configuratorable {
    void setConfigurator(Configurator configurator);
}
