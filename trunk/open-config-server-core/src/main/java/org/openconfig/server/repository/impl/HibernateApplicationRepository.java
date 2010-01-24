package org.openconfig.server.repository.impl;

import org.openconfig.server.repository.NoSuchApplicationFoundException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.openconfig.server.repository.ApplicationRepository;
import org.openconfig.server.domain.Application;

import java.util.List;

import static org.springframework.util.Assert.notNull;

/**
 * The Hibernate backed implemention of the ApplicationRepository.
 * @author Richard L. Burton III - SmartCode LLC
 */
@Transactional(readOnly = true)
public class HibernateApplicationRepository extends HibernateTemplate implements ApplicationRepository {

    /** The index of the first element found in the repository. */
    private static final int FIRST_ELEMENT = 0;

    @SuppressWarnings("unchecked")
    public Application findByName(String name) {
        List<Application> matches = find("FROM Application where name = ?", name);
        if (matches.isEmpty()) {
            throw new NoSuchApplicationFoundException("No matches were found for application named '" + name + "'.", name);
        }
        return matches.get(FIRST_ELEMENT);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Application application) {
        notNull(application, "The application parameter can not be null.");
        super.save(application);
    }

}
