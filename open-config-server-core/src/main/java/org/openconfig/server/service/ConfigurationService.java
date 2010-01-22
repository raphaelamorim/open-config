package org.openconfig.server.service;

import org.openconfig.server.domain.Application;
import org.openconfig.server.domain.Configuration;

import java.util.Set;
import java.util.Map;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public interface ConfigurationService {

    Application findApplication(String name);

    Map<String, Configuration> findConfigurationsByApplication(String application);

    void saveApplication(Application application);
    
}
