package org.openconfig.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import org.openconfig.core.bean.PropertyNormalizer;
import org.openconfig.core.bean.LowercasePropertyNormalizer;
import org.openconfig.core.OpenConfigContext;
import org.openconfig.core.BasicOpenConfigContext;
import org.openconfig.core.EnvironmentResolver;
import org.openconfig.core.SystemPropertyEnvironmentResolver;
import org.openconfig.factory.impl.ConfiguratorFactoryImpl;
import org.openconfig.factory.ConfiguratorFactory;
import org.openconfig.MutableConfigurator;
import org.openconfig.ioc.config.OpenConfigConfiguration;
import org.openconfig.ioc.config.PropertiesOpenConfigConfiguration;
import org.openconfig.configurators.MapConfigurator;

import java.util.List;
import java.util.LinkedList;

/**
 * This class is used to initialize Guice with the ability to override
 * certain points within OpenConfig. It will locate one of the following
 * OpenConfig configuration files in the below order:
 * <p/>
 * <ol>
 * <li>META-INF/open-config.properties</li>
 * <li>META-INF/open-config.xml</li>
 * </ol>
 *
 * @author Richard L. Burton III
 */
@SuppressWarnings("unchecked")
public class OpenConfigModule extends AbstractModule {

    private ConfigurationLocator configurationLocator;

    private OpenConfigConfiguration openConfigConfiguration;

    protected void configure() {
        processConfigurationFiles();
        configurationLocator.locate();
        openConfigConfiguration = configurationLocator.getOpenConfigConfiguration();

        bind(PropertyNormalizer.class)
                .to(getProviderClass(PropertyNormalizer.class))
                .in(Singleton.class);

        bind(OpenConfigContext.class)
                .to(getProviderClass(OpenConfigContext.class))
                .in(Singleton.class);

        bind(EnvironmentResolver.class)
                .to(getProviderClass(EnvironmentResolver.class))
                .in(Singleton.class);

        bind(ConfiguratorFactory.class)
                .to(getProviderClass(ConfiguratorFactory.class))
                .in(Singleton.class);

        bind(MutableConfigurator.class)
                .to(getProviderClass(MutableConfigurator.class));

    }

    /**
     * Builds the OpenConfigConfiguration consumers.
     */
    private void processConfigurationFiles() {
        List<OpenConfigConfiguration> configurationManagers = new LinkedList<OpenConfigConfiguration>();
        configurationManagers.add(new PropertiesOpenConfigConfiguration());
        configurationLocator = new ConfigurationLocator(configurationManagers);
    }

    protected Class getProviderClass(Class clazz) {
        return openConfigConfiguration.getClass(clazz.getSimpleName());
    }

}
