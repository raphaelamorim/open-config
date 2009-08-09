package org.openconfig.core.bean;

/**
 * Defines the contract for normalizing the provided property name expected
 * by OpenConfig.
 *
 * @author Richard L. Burton III
 */
public interface PropertyNormalizer {

    /**
     * Normalizes the property name according to the internal rules.
     *
     * @param name The property name to normalize.
     * @return The normalize version of the property.
     */
    String normalize(String name);

}
