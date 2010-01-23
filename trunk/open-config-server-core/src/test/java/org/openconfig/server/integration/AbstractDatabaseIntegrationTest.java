package org.openconfig.server.integration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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

    @BeforeClass
    public final void setup() {
        Session session = SessionFactoryUtils.getSession(sessionFactory, true);
        TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
    }

    @AfterClass
    public final void teardown() {
        SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
        SessionFactoryUtils.closeSession(sessionHolder.getSession());
    }
}
