package org.openconfig.server.repository;

import org.openconfig.server.domain.Application;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public interface ApplicationRepository {

    Application findByName(String name);

    void save(Application application);

}
