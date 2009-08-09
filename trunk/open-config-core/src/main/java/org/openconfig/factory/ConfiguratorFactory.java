package org.openconfig.factory;

/**
 * @author Richard L. Burton III
 */
public interface ConfiguratorFactory {

    /**
     * Creates a proxy of the provide class that abstracts the <tt>Configurator</tt> API from the developers code base.
     * This allows for a small API footprint within the developers code base.
     * @param clazz The interface or class to proxy.
     * @param <T> A proxy instance of the class provided.
     * @return A proxy instance of the class provided.
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    <T> T newInstance(Class clazz) throws IllegalAccessException, InstantiationException;

    /**
     * Creates a proxy of the provide class that abstracts the <tt>Configurator</tt> API from the developers code base.
     * This allows for a small API footprint within the developers code base.
     * @param clazz The interface or class to proxy.
     * @param <T> A proxy instance of the class provided.
     * @param alias A boolean that tells OpenConfig if it should usee the Class#getSimpleName() as a prefix.
     * @return A proxy instance of the class provided.
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    <T> T newInstance(Class clazz, boolean alias) throws IllegalAccessException, InstantiationException;
}
