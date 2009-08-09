package org.openconfig;

/**
 * This interface defines the mutable version of the Configurator that
 * only provides 'read/write access' to properties managed by OpenConfig.
 *
 * @author Richard L. Burton III
 */
public interface MutableConfigurator extends Configurator {

    void setValue(String name, Object value);

}
