package org.openconfig.server.repository.impl;

import org.openconfig.server.domain.Configuration;
import org.openconfig.server.repository.ConfigurationRepository;
import org.openconfig.server.repository.NoSuchConfigurationFoundException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.util.Assert.notNull;

/**
 * A Hibernate backed implementation of the ConfigurationRepository.
 * @author Dushyanth (Dee) Inguva - SmartCode LLC
 */
@Transactional(readOnly = true)
public class HibernateConfigurationRepository extends HibernateTemplate implements ConfigurationRepository {

    private static final int FIRST_ELEMENT = 0;

    @SuppressWarnings("unchecked")
    public Configuration findByName(String name) {
        List<Configuration> matches = find("FROM Configuration where name = ?", name);
        if (matches.isEmpty()) {
            throw new NoSuchConfigurationFoundException("No matches were found for configuration named '" + name + "'.", name);
        }
        return matches.get(FIRST_ELEMENT);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Configuration configuration) {
        notNull(configuration, "The configuration parameter can not be null.");
        super.save(configuration);
    }
    
}