package org.openconfig.transformers;

/**
 * @author Richard L. Burton III
 */
public interface Transformer<F, T> {

    T transform(F source);
}
