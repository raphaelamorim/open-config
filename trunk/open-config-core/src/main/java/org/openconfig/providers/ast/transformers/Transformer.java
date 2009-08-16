package org.openconfig.providers.ast.transformers;

/**
 * @author Richard L. Burton III
 */
public interface Transformer<F, T> {

    T transform(F source) throws Exception;
}
