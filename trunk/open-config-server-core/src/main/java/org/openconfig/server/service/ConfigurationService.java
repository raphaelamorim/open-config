package org.openconfig.server.service;

import org.openconfig.server.domain.Application;
import org.openconfig.server.domain.Configuration;

import java.util.Set;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public interface ConfigurationService {

    Application findApplication(String name);

    Set<Configuration> findConfigurationsByApplication(String application);

    void saveApplication(Application application);
    
}
