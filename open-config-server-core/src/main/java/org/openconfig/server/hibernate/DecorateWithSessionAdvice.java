package org.openconfig.server.hibernate;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Opens a Hibernate session around a method that defines the annotation @DecorateWithSession
 *
 * @author Dushyanth (Dee) Inguva - SmartCode LLC
 */
@Aspect
public class DecorateWithSessionAdvice {

    private SessionFactory sessionFactory;

    @Required
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Around("@annotation(org.openconfig.server.hibernate.DecorateWithSession)")
    public Object decorateWithHibernateSession(ProceedingJoinPoint joinPoint) throws Throwable {

        Session session = SessionFactoryUtils.getSession(sessionFactory, true);
        TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
        try {
            return joinPoint.proceed();
        }
        finally {
            SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
            SessionFactoryUtils.closeSession(sessionHolder.getSession());
        }
    }
}
