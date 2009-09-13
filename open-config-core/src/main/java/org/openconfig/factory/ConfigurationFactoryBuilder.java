package org.openconfig.factory;

import org.openconfig.core.BasicOpenConfigContext;
import org.openconfig.core.OpenConfigContext;
import org.openconfig.core.EnvironmentResolver;
import org.openconfig.ioc.ConfigurationLocator;
import static org.openconfig.ioc.ConfigurationLocator.PROPERTIES_FILE;
import static org.openconfig.ioc.ConfigurationLocator.XML_FILE;
import org.openconfig.ioc.config.OpenConfigConfiguration;
import org.openconfig.ioc.config.PropertiesOpenConfigConfiguration;
import org.openconfig.ioc.config.XmlOpenConfigConfiguration;
import org.openconfig.providers.DataProvider;

import java.util.LinkedHashMap;

/**
 * @author Dushyanth (Dee) Inguva
 */
public class ConfigurationFactoryBuilder {

    private Class<EnvironmentResolver> environmentResolverClass;

    private OpenConfigContext openConfigContext;

    private ConfigurationLocator configurationLocator;

    private OpenConfigConfiguration openConfigConfiguration;

    public ConfigurationFactoryBuilder setOpenConfigCotext(OpenConfigContext openConfigContext) {
        this.openConfigContext = openConfigContext;
        return this;
    }

    public ConfigurationFactoryBuilder setEnvironmentResolverClass(Class<EnvironmentResolver> environmentResolverClass) {
        this.environmentResolverClass = environmentResolverClass;
        return this;
    }

    public ConfiguratorFactory build() {
        processConfigurationFiles();
        configurationLocator.locate();
        openConfigConfiguration = configurationLocator.getOpenConfigConfiguration();
        throw new UnsupportedOperationException("Method not coded yet");
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
}
