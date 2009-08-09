package org.openconfig;

import org.openconfig.core.ConfiguratorProxy;
import org.openconfig.core.EnvironmentResolver;
import org.openconfig.core.bean.PropertyNormalizer;
import org.openconfig.core.bean.ProxyInvocationHandler;
import org.openconfig.core.bean.ConfiguratorProxyInvocationHandler;
import org.openconfig.factory.ConfiguratorFactory;
import org.openconfig.ioc.OpenConfigModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Currently, there is no IoC container being used, so this ObjectFactory
 * will be used to abstract the construction specific concert types of classes.
 *
 * @author Richard L. Burton III
 */
public class ObjectFactory {

    private static final ObjectFactory INSTANCE = new ObjectFactory();

    private Injector injector;

    private ObjectFactory() {
        injector = Guice.createInjector(new OpenConfigModule());
    }

    public static ObjectFactory getInstance() {
        return INSTANCE;
    }

    public ConfiguratorProxy newConfiguratorProxy(Class configuratorInterface, boolean alias) {
        MutableConfigurator configurator = injector.getInstance(MutableConfigurator.class);
        PropertyNormalizer propertyNormalizer = injector.getInstance(PropertyNormalizer.class);

        // TODO: Move this into Guice
        ConfiguratorProxy proxy = new ConfiguratorProxy(configuratorInterface, alias);
        ProxyInvocationHandler returnHandler = new ConfiguratorProxyInvocationHandler(proxy);
        returnHandler.setMutableConfigurator(configurator);
        proxy.setPropertyNormalizer(propertyNormalizer);
        proxy.setProxyInvocationHandler(returnHandler);
        return proxy;
    }

    public ConfiguratorFactory newConfiguratorFactory() {
        return injector.getInstance(ConfiguratorFactory.class);
    }

    public <T> T construct(String clazzName) {
        try {
            Class clazz = Class.forName(clazzName);
            return (T) clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public EnvironmentResolver getDefaultEnvironmentResolver() {
        return injector.getInstance(EnvironmentResolver.class);
    }
}
