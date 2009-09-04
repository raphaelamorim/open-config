package org.openconfig.transformers;

/**
 * @author Richard L. Burton III
 */
public interface Transformer<F, T> {

    /** todo: This method shouldn't throw a checked exception! */
    T transform(F source);
}
