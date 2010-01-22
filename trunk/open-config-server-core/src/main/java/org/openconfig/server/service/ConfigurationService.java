package org.openconfig.server.service;

import org.openconfig.server.domain.Configuration;
import org.openconfig.server.repository.ConfigurationRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class ConfigurationService {
    private ConfigurationRepository configurationRepository;

    public Configuration findConfiguration(String name) {
        return configurationRepository.findByName(name);
    }

    @Transactional
    public void saveConfiguration(Configuration configuration) {
        configurationRepository.save(configuration);
    }

    public void setConfigurationRepository(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

}
