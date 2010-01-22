package org.openconfig.server.repository;

import org.openconfig.server.domain.Application;
import org.openconfig.server.domain.Configuration;

/**
 * @author Dushyanth (Dee) Inguva - SmartCode LLC
 */
public interface ConfigurationRepository {

    Configuration findByName(String name);

    void save(Configuration configuration);
}
