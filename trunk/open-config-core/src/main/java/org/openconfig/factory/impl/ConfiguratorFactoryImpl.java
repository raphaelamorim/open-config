package org.openconfig.factory.impl;

import org.openconfig.factory.ConfiguratorFactory;
import static org.openconfig.ObjectFactory.getInstance;
import org.openconfig.core.ConfiguratorProxy;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author Richard L. Burton III
 */
@SuppressWarnings("unchecked")
public class ConfiguratorFactoryImpl implements ConfiguratorFactory {

    private static final Logger LOGGER = Logger.getLogger(ConfiguratorFactoryImpl.class);

    /**
     * @see ConfiguratorFactory#newInstance(Class, boolean);
     */
    public <T> T newInstance(final Class clazz, final boolean alias) throws IllegalAccessException, InstantiationException {
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

        enhancer.setCallback(new MethodInterceptor() {
            public Object intercept(Object source, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
                ConfiguratorProxy proxy = getInstance().newConfiguratorProxy(clazz, alias);
                return proxy.intercept(source, method, arguments, methodProxy);
            }
        });
        return (T) enhancer.create();
    }

    /**
     * @see ConfiguratorFactory#newInstance(Class);
     */
    public <T> T newInstance(final Class clazz) throws IllegalAccessException, InstantiationException {
        return (T) newInstance(clazz, true);
    }
    
}
