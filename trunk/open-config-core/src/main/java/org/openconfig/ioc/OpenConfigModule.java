package org.openconfig.ioc;

import java.util.LinkedHashMap;

import org.openconfig.MutableConfigurator;
import org.openconfig.event.EventPublisher;
import org.openconfig.providers.DataProvider;
import org.openconfig.providers.CompositeDataProvider;
import org.openconfig.core.EnvironmentResolver;
import org.openconfig.core.OpenConfigContext;
import org.openconfig.core.BasicOpenConfigContext;
import org.openconfig.core.bean.PropertyNormalizer;
import org.openconfig.factory.ConfiguratorFactory;
import org.openconfig.ioc.config.OpenConfigConfiguration;
import org.openconfig.ioc.config.PropertiesOpenConfigConfiguration;
import org.openconfig.ioc.config.XmlOpenConfigConfiguration;
import static org.openconfig.ioc.ConfigurationLocator.*;
import com.google.inject.*;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.binder.ScopedBindingBuilder;

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

    private ConfigurationLocator configurationLocator;

    private OpenConfigConfiguration openConfigConfiguration;

    public static final String OPEN_CONFIG_DEVELOPMENT_MODE = "openconfig.dev";
    public static final String OPEN_CONFIG_DEVELOPMENT_FILE = "openconfig.app.config";

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

        bind(EventPublisher.class)
                .to(getProviderClass(EventPublisher.class));

        if(isInLocalDevelopment()){
            bind(DataProvider.class)
                    .to(CompositeDataProvider.class);
        }else{
            bind(DataProvider.class)
                .toInstance(getDataProvider());
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

    protected Class getProviderClass(Class clazz) {
        return openConfigConfiguration.getClass(clazz.getSimpleName());
    }

    public DataProvider getDataProvider() {
        Class dataProviderClass = getProviderClass(DataProvider.class);
        try {
            DataProvider dataProvider = (DataProvider) dataProviderClass.newInstance();
            dataProvider.initialize(new BasicOpenConfigContext());
            return dataProvider;
        } catch (InstantiationException e) {
            throw new IllegalArgumentException("Class failed to get created.");
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Class failed to get created.");
        }
    }

    protected boolean isInLocalDevelopment(){
        String developmentMode = System.getProperty(OPEN_CONFIG_DEVELOPMENT_MODE);
        return Boolean.parseBoolean(developmentMode);
    }

}
