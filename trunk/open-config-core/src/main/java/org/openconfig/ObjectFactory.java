package org.openconfig;

import org.openconfig.core.ConfiguratorProxy;
import org.openconfig.core.EnvironmentResolver;
import org.openconfig.core.BasicOpenConfigContext;
import org.openconfig.core.OpenConfigContext;
import org.openconfig.core.bean.PropertyNormalizer;
import org.openconfig.core.bean.ProxyInvocationHandler;
import org.openconfig.core.bean.ConfiguratorProxyInvocationHandler;
import org.openconfig.factory.ConfiguratorFactory;
import org.openconfig.ioc.OpenConfigModule;
import org.openconfig.event.EventPublisher;
import org.openconfig.event.EventListener;
import org.openconfig.providers.DataProvider;
import org.openconfig.providers.CompositeDataProvider;
import org.openconfig.providers.PropertiesDataProvider;
import com.google.inject.Guice;
import com.google.inject.Injector;

import static java.util.Collections.singletonMap;

/**
 * Currently, there is no IoC container being used, so this ObjectFactory
 * will be used to abstract the construction specific concert types of classes.
 *
 * @author Richard L. Burton III
 * @author Dushyanth (Dee) Inguva
 * @depreciated
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

    /**
     * @param configuratorInterface
     * @param alias
     * @param eventListeners
     * @return
     * @todo refactor to improve the object creation by Guice.
     */
    public ConfiguratorProxy newConfiguratorProxy(Class configuratorInterface, boolean alias, EventListener... eventListeners) {

        DataProvider dataProvider = injector.getInstance(DataProvider.class);

        if (dataProvider instanceof CompositeDataProvider) {
            CompositeDataProvider cdp = (CompositeDataProvider) dataProvider;
            String name = configuratorInterface.getSimpleName();
            if (cdp.missingDataProvider(name)) {
                DataProvider propertiesDataProvider = new PropertiesDataProvider(); // TODO: Remove this out somehow.
                OpenConfigContext context = new BasicOpenConfigContext(singletonMap("interface", configuratorInterface.getSimpleName()));
                propertiesDataProvider.initialize(context);
                cdp.addDataProvider(name, propertiesDataProvider);
            }
        }

        PropertyNormalizer propertyNormalizer = injector.getInstance(PropertyNormalizer.class);
        EventPublisher eventPublisher = injector.getInstance(EventPublisher.class);
        eventPublisher.addListeners(eventListeners);

        ConfiguratorProxy proxy = new ConfiguratorProxy(configuratorInterface, alias, eventPublisher);
        ProxyInvocationHandler returnHandler = new ConfiguratorProxyInvocationHandler(proxy);
        proxy.setDataProvider(dataProvider);
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
