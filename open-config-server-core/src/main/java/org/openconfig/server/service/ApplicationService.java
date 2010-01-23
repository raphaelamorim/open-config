package org.openconfig.server.service;

import org.openconfig.server.domain.Application;
import org.openconfig.server.domain.Configuration;
import org.openconfig.server.repository.ApplicationRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * Responsible to persist, and retreive the application.
 *
 * @author Dushyanth (Dee) Inguva - SmartCode LLC
 */
public class ApplicationService {
    private ApplicationRepository applicationRepository;

    public Application findApplication(String name) {
        Assert.notNull(name);
        return applicationRepository.findByName(name);
    }

    public Map<String, Configuration> findConfigurationsByApplication(String name) {
        Assert.notNull(name);
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
