package org.openconfig.server.integration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Sets up and tears down a Hibernate session to replicate OpenSessionInFilter functionality.
 *
 * @author Dushyanth (Dee) Inguva - SmartCode LLC
 */
public class AbstractDatabaseIntegrationTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Before
    public final void setupSession() {
        Session session = SessionFactoryUtils.getSession(sessionFactory, true);
        TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
    }

    @After
    public final void tearDownSession() {
        SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
        SessionFactoryUtils.closeSession(sessionHolder.getSession());
    }
}
