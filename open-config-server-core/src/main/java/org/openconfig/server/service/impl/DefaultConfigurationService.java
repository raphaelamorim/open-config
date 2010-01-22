package org.openconfig.server.service.impl;

import org.openconfig.server.service.ConfigurationService;
import org.openconfig.server.domain.Application;
import org.openconfig.server.domain.Configuration;
import org.openconfig.server.repository.ApplicationRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class DefaultConfigurationService implements ConfigurationService {

    private ApplicationRepository applicationRepository;

    public Application findApplication(String name) {
        return applicationRepository.findByName(name);
    }

    public Set<Configuration> findConfigurationsByApplication(String name) {
        Application application = applicationRepository.findByName(name);
        return application.getConfigurations();
    }

    @Transactional
    public void saveApplication(Application application) {
        applicationRepository.save(application);
    }

    public void setApplicationRepository(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

}
