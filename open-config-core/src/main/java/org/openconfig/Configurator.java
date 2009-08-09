package org.openconfig;

/**
 * This interface defines the immutable version of the Configurator that
 * only provides 'read access' to properties managed by OpenConfig.
 *
 * @author Richard L. Burton III
 */
public interface Configurator {

    /**
     * This method locates the value stored in the OpenConfig central server.
     *
     * @param key The configuration parameter to lookup.
     * @return The value stored in the OpenConfig central server.
     */
    Object getValue(String key);

}
