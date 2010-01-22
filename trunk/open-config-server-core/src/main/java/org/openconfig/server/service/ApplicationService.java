package org.openconfig.server.service;

import org.openconfig.server.domain.Application;
import org.openconfig.server.domain.Configuration;
import org.openconfig.server.repository.ApplicationRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author Dushyanth (Dee) Inguva - SmartCode LLC
 */
public class ApplicationService {
    private ApplicationRepository applicationRepository;

    public Application findApplication(String name) {
        return applicationRepository.findByName(name);
    }

    public Map<String, Configuration> findConfigurationsByApplication(String name) {
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
