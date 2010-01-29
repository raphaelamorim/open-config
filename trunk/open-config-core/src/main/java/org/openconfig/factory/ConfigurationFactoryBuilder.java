package org.openconfig.factory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.log4j.Logger;
import org.openconfig.Environment;
import org.openconfig.core.EnvironmentResolver;
import org.openconfig.core.OpenConfigContext;
import org.openconfig.core.SystemPropertyEnvironmentResolver;
import org.openconfig.core.bean.PropertyNormalizer;
import org.openconfig.event.EventPublisher;
import org.openconfig.factory.impl.ClasspathConfigurationLocator;
import org.openconfig.factory.impl.InternalConfigurationLocator;
import org.openconfig.ioc.ConfigurationFileProcessor;

import org.openconfig.ioc.OpenConfigModule;
import org.openconfig.ioc.config.OpenConfigConfiguration;
import org.openconfig.providers.CompositeDataProvider;
import org.openconfig.providers.DataProvider;

import java.net.URL;

import static org.openconfig.util.Assert.notNull;

/**
 * TODO: Refactor this to ConfiguratorFactoryBuilder
 * TODO: Verify the parameters...
 *
 * @author Dushyanth (Dee) Inguva
 */
public class ConfigurationFactoryBuilder {

    private Logger LOGGER = Logger.getLogger(ConfigurationFactoryBuilder.class);

    private Class<? extends EnvironmentResolver> environmentResolverClass = SystemPropertyEnvironmentResolver.class;

    private OpenConfigContext openConfigContext;

    private final ConfigurationLocator internalConfigurationLocator = new InternalConfigurationLocator();

    private ConfigurationLocator configurationLocator = new ClasspathConfigurationLocator();

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
    public final ConfiguratorFactory build() {
        ConfigurationFileProcessor processor;
        try {
            URL internalConfigurationFile = internalConfigurationLocator.locate();
            processor = new ConfigurationFileProcessor(internalConfigurationFile);
        } catch (NoConfigurationFileFoundException ncfe) {
            throw new RuntimeException("Oops! No internal configuration file was found!");
        }

        URL custonConfigurationFile = null;
        try {
            custonConfigurationFile = configurationLocator.locate();
        } catch (NoConfigurationFileFoundException e) {
            LOGGER.debug("No configuration file for ConfigurationLocator of type " + configurationLocator.getClass().getName());
        }
        openConfigConfiguration = processor.process(custonConfigurationFile);

        OpenConfigModule openConfigModule = createOpenConfigModule();
        Injector injector = Guice.createInjector(openConfigModule);
        return injector.getInstance(ConfiguratorFactory.class);
    }

    /**
     * Creates the openconfig module that is used to configure guice.
     *
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

    public void setConfigurationLocator(ConfigurationLocator configurationLocator) {
        notNull(configurationLocator, "The parameter 'configurationLocator' was null.");
        this.configurationLocator = configurationLocator;
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
