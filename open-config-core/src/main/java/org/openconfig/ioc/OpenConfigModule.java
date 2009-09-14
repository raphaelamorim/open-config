package org.openconfig.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import org.openconfig.core.EnvironmentResolver;
import org.openconfig.core.OpenConfigContext;
import org.openconfig.core.bean.PropertyNormalizer;
import org.openconfig.event.EventPublisher;
import org.openconfig.factory.ConfiguratorFactory;
import org.openconfig.providers.DataProvider;

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
 * @author Dushyanth (Dee) Inguva
 */
@SuppressWarnings("unchecked")
public class OpenConfigModule extends AbstractModule {

    public static final String OPEN_CONFIG_DEVELOPMENT_MODE = "openconfig.dev";

    public static final String OPEN_CONFIG_DEVELOPMENT_FILE = "openconfig.app.config";

    private OpenConfigContext openConfigContext;

    private EnvironmentResolver environmentResolver;

    private Class<? extends ConfiguratorFactory> configuratorFactoryClass;

    private Class<? extends PropertyNormalizer> propertyNormalizerClass;
    private Class<? extends EventPublisher> eventPublisherClass;
    private DataProvider dataProvider;

    protected void configure() {

        bind(PropertyNormalizer.class)
                .to(propertyNormalizerClass)
                .in(Singleton.class);

        bind(OpenConfigContext.class)
                .toInstance(openConfigContext);

        bind(EnvironmentResolver.class)
                .toInstance(environmentResolver);

        bind(ConfiguratorFactory.class)
                .to(configuratorFactoryClass);

        bind(EventPublisher.class)
                .to(eventPublisherClass)
                .in(Singleton.class);

        bind(DataProvider.class)
                .toInstance(dataProvider);
    }

    public void setOpenConfigContext(OpenConfigContext openConfigContext) {
        this.openConfigContext = openConfigContext;
    }

    public void setEnvironmentResolver(EnvironmentResolver environmentResolver) {
        this.environmentResolver = environmentResolver;
    }

    public void setConfiguratorFactoryClass(Class<? extends ConfiguratorFactory> configuratorFactoryClass) {
        this.configuratorFactoryClass = configuratorFactoryClass;
    }

    public void setPropertyNormalizerClass(Class<? extends PropertyNormalizer> propertyNormalizerClass) {
        this.propertyNormalizerClass = propertyNormalizerClass;
    }

    public void setEventPublisherClass(Class<? extends EventPublisher> eventPublisherClass) {
        this.eventPublisherClass = eventPublisherClass;
    }

    public void setDataProvider(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }
}
