package org.openconfig.server.repository.impl;

import org.openconfig.server.domain.Configuration;
import org.openconfig.server.repository.ConfigurationRepository;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Dushyanth (Dee) Inguva - SmartCode LLC
 */
@Transactional(readOnly = true)
public class HibernateConfigurationRepository extends HibernateTemplate implements ConfigurationRepository {

    private static final int FIRST_ELEMENT = 0;

    @SuppressWarnings("unchecked")
    public Configuration findByName(String name) {
        List<Configuration> matches = find("FROM Configuration where name = ?", name);
        if (matches.isEmpty()) {
            throw new RuntimeException("No matches were found for configuration named '" + name + "'.");
        }
        return matches.get(FIRST_ELEMENT);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Configuration application) {
        super.save(application);
    }
}