package org.openconfig.server.repository.impl;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.openconfig.server.repository.ApplicationRepository;
import org.openconfig.server.domain.Application;

import java.util.List;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
@Transactional(readOnly = true)
public class HibernateApplicationRepository extends HibernateTemplate implements ApplicationRepository {

    private static final int FIRST_ELEMENT = 0;

    @SuppressWarnings("unchecked")
    public Application findByName(String name) {
        List<Application> matches = find("FROM Application where name = ?", name);
        if (matches.isEmpty()) {
            throw new RuntimeException("No matches were found for application named '" + name + "'.");
        }
        return matches.get(FIRST_ELEMENT);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Application application) {
        super.save(application);
    }

}
