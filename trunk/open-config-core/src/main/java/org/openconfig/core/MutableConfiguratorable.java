package org.openconfig.core;

import org.openconfig.MutableConfigurator;

/**
 * @author Richard L. Burton III
 */
public interface MutableConfiguratorable {
    void setMutableConfigurator(MutableConfigurator mutableConfigurator);
}
