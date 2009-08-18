package org.openconfig.factory.impl;

import org.openconfig.factory.ConfiguratorFactory;
import static org.openconfig.ObjectFactory.getInstance;
import org.openconfig.core.ConfiguratorProxy;
import org.openconfig.event.EventListener;
import org.openconfig.event.EventPublisher;
import org.openconfig.event.ChangeStateEvent;
import org.openconfig.Configurator;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author Richard L. Burton III
 * @author Dushyanth (Dee) Inguva
 */
@SuppressWarnings("unchecked")
public class DefaultConfiguratorFactory implements ConfiguratorFactory {

    private static final Logger LOGGER = Logger.getLogger(DefaultConfiguratorFactory.class);

    /**
     * @see ConfiguratorFactory#(Class, boolean, EventListener)
     */
    public <T> T newInstance(final Class clazz, final boolean prefix, EventListener... eventListeners){
        Enhancer enhancer = new Enhancer();

        if (clazz.isInterface()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Creating a proxy for the following interface: " + clazz.getName());
            }
            enhancer.setInterfaces(new Class[]{clazz});
        } else {

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Creating a proxy for the following class: " + clazz.getName());
            }
            enhancer.setSuperclass(clazz);
        }


        final ConfiguratorProxy proxy = getInstance().newConfiguratorProxy(clazz, prefix, eventListeners);

        enhancer.setCallback(new MethodInterceptor() {
            public Object intercept(Object source, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
               return proxy.intercept(source, method, arguments, methodProxy);
            }
        });
        return (T) enhancer.create();
    }

    public Configurator newInstance(EventListener... eventListeners) {
        return newInstance(Configurator.class, eventListeners);
    }

    /**
     * 
     */
    public <T> T newInstance(final Class clazz, EventListener... eventListeners){
        return (T) newInstance(clazz, true, eventListeners);
    }
    
}
