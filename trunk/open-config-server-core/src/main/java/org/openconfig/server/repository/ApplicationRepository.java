package org.openconfig.server.repository;

import org.openconfig.server.domain.Application;

/**
 * This class handles the persistence of the Application domain class.
 * @author Richard L. Burton III - SmartCode LLC
 */
public interface ApplicationRepository {

    /**
     * This method finds an Application by the provided name.
     * @param name The name of the application to locate.
     * @return The application found in the system.
     * @throws NoSuchApplicationFoundException Thrown when the application isn't found.
     */
    Application findByName(String name) throws NoSuchApplicationFoundException;

    /**
     * This method saves the supplied Application into the backing repository.
     * @param application The application to be saved.
     * @throws IllegalArgumentException When the Application is null.
     */
    void save(Application application);

}
