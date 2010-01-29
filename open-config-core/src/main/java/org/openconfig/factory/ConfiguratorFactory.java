package org.openconfig.factory;

import org.openconfig.event.EventListener;
import org.openconfig.Configurator;

/**
 * @author Richard L. Burton III
 */
public interface ConfiguratorFactory {

    /**
     * Creates a configurator
     *
     * @param eventListeners
     * @return
     */
    Configurator newInstance(EventListener... eventListeners);


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
    <T> T newInstance(Class clazz, EventListener... eventListeners);
}
