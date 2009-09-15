package org.openconfig.factory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.openconfig.Environment;
import org.openconfig.core.EnvironmentResolver;
import org.openconfig.core.OpenConfigContext;
import org.openconfig.core.SystemPropertyEnvironmentResolver;
import org.openconfig.core.bean.PropertyNormalizer;
import org.openconfig.event.EventPublisher;
import org.openconfig.ioc.ConfigurationLocator;
import static org.openconfig.ioc.ConfigurationLocator.PROPERTIES_FILE;
import static org.openconfig.ioc.ConfigurationLocator.XML_FILE;
import org.openconfig.ioc.OpenConfigModule;
import org.openconfig.ioc.config.OpenConfigConfiguration;
import org.openconfig.ioc.config.PropertiesOpenConfigConfiguration;
import org.openconfig.ioc.config.XmlOpenConfigConfiguration;
import org.openconfig.providers.CompositeDataProvider;
import org.openconfig.providers.DataProvider;

import java.util.LinkedHashMap;

/**
 * TODO: Refactor this to ConfiguratorFactoryBuilder
 * @author Dushyanth (Dee) Inguva
 */
public class ConfigurationFactoryBuilder {

    private Class<? extends EnvironmentResolver> environmentResolverClass = SystemPropertyEnvironmentResolver.class;

    private OpenConfigContext openConfigContext;

    private ConfigurationLocator configurationLocator;

    private OpenConfigConfiguration openConfigConfiguration;

    /**
     * Sets the open config context.
     * <p/>
     * (Optional: an empty OpenConfigContext is created by default)
     *
     * @param openConfigContext the OpenConfigContext to set
     * @return this object in trying to be a good builder
     */
    public ConfigurationFactoryBuilder setOpenConfigContext(OpenConfigContext openConfigContext) {
        this.openConfigContext = openConfigContext;
        return this;
    }

    /**
     * Sets the open config context.
     * <p/>
     * (Optional: {@link org.openconfig.core.SystemPropertyEnvironmentResolver} is used by default)
     *
     * @param environmentResolverClass the environmentResolverClass to use
     * @return this object in trying to be a good builder
     */
    public ConfigurationFactoryBuilder setEnvironmentResolverClass(Class<? extends EnvironmentResolver> environmentResolverClass) {
        this.environmentResolverClass = environmentResolverClass;
        return this;
    }

    /**
     * @return the created ConfiguratorFactory
     */
    public ConfiguratorFactory build() {
        processConfigurationFiles();
        configurationLocator.locate();
        openConfigConfiguration = configurationLocator.getOpenConfigConfiguration();

        OpenConfigModule openConfigModule = createOpenConfigModule();

        Injector injector = Guice.createInjector(openConfigModule);
        return injector.getInstance(ConfiguratorFactory.class);
    }

    /**
     * Creates the openconfig module that is used to configure guice.
     * TODO do we need a guice builder?
     * @return the guice module
     */
    private OpenConfigModule createOpenConfigModule() {
        OpenConfigModule openConfigModule = new OpenConfigModule();
        openConfigModule.setEventPublisherClass(getProviderClass(EventPublisher.class));
        openConfigModule.setPropertyNormalizerClass(getProviderClass(PropertyNormalizer.class));
        openConfigModule.setOpenConfigConfiguration(openConfigConfiguration);
        EnvironmentResolver resolver = createInstance(environmentResolverClass);
        openConfigModule.setEnvironmentResolver(resolver);

        if (openConfigContext == null) {
            setOpenConfigContext(createInstance(getProviderClass(OpenConfigContext.class)));
        }
        openConfigModule.setOpenConfigContext(openConfigContext);

        Environment environment = resolver.resolve(openConfigContext);

        openConfigModule.setDataProviderClass(getDataProviderClass(environment));

        openConfigModule.setConfiguratorFactoryClass(getProviderClass(ConfiguratorFactory.class));
        return openConfigModule;
    }

    private <T> T createInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Could not instantiate class: " + clazz, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Could not instantiate class: " + clazz, e);
        }
    }

    /**
     * Builds the OpenConfigConfiguration consumers.
     */
    private void processConfigurationFiles() {
        LinkedHashMap<String, OpenConfigConfiguration> configurationManagers = new LinkedHashMap<String, OpenConfigConfiguration>();
        configurationManagers.put(PROPERTIES_FILE, new PropertiesOpenConfigConfiguration());
        configurationManagers.put(XML_FILE, new XmlOpenConfigConfiguration());
        configurationLocator = new ConfigurationLocator(configurationManagers);
    }

    protected <T> Class<? extends T> getProviderClass(Class<T> clazz) {
        return (Class<? extends T>) openConfigConfiguration.getClass(clazz.getSimpleName());
    }

    /**
     * Determines what data provider to use based on the environment.
     *
     * @param environment the current environment
     * @return the data provider
     */
    protected Class<? extends DataProvider> getDataProviderClass(Environment environment) {

        Class<? extends DataProvider> dataProviderClass;
        if (environment.isLocal()) {
            dataProviderClass = CompositeDataProvider.class;
        } else {
            dataProviderClass = getProviderClass(DataProvider.class);
        }

        return dataProviderClass;
    }
}
