package org.openconfig.spring;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.openconfig.factory.ConfiguratorFactory;
import org.openconfig.factory.impl.DefaultConfiguratorFactory;
import org.openconfig.Configurator;

import java.util.Properties;

/**
 * @author Richard L. Burton III
 */
public class OpenConfigPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private Configurator configurator;

    public OpenConfigPropertyPlaceholderConfigurer() {
        super();
        ConfiguratorFactory configuratorFactory = new DefaultConfiguratorFactory();
        configurator = configuratorFactory.newInstance();
    }

}
