package org.openconfig.factory;

import org.openconfig.event.EventListener;
import org.openconfig.Configurator;

/**
 *
 *
 * @author Richard L. Burton III
 */
public interface ConfiguratorFactory {

    /**
     * Creates a configurator
     * 
     * @param eventListeners
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    Configurator newInstance(EventListener... eventListeners) throws IllegalAccessException, InstantiationException;


    /**
     * Creates a proxy of the provide class that abstracts the <tt>Configurator</tt> API from the developers code base.
     * This allows for a small API footprint within the developers code base.
     *
     * @param clazz          The interface or class to proxy.
     * @param <T>            A proxy instance of the class provided.
     * @param eventListeners
     * @return A proxy instance of the class provided.
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    <T> T newInstance(Class clazz, EventListener... eventListeners) throws IllegalAccessException, InstantiationException;

    /**
     * Creates a proxy of the provide class that abstracts the <tt>Configurator</tt> API from the developers code base.
     * This allows for a small API footprint within the developers code base.
     *
     * @param clazz          The interface or class to proxy.
     * @param <T>            A proxy instance of the class provided.
     * @param prefix         A boolean that tells OpenConfig if it should use the Class#getSimpleName() as a prefix.
     * @param eventListeners
     * @return A proxy instance of the class provided.
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    <T> T newInstance(Class clazz, boolean prefix, EventListener... eventListeners) throws IllegalAccessException, InstantiationException;
}
