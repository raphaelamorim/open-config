package org.openconfig.server.mbean;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openconfig.server.domain.Application;
import org.openconfig.server.repository.NoSuchApplicationFoundException;
import org.openconfig.server.service.ApplicationService;
import org.openconfig.server.transformer.OpenMBeanApplicationTransformer;
import org.openconfig.server.hibernate.DecorateWithSession;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.management.openmbean.CompositeData;

/**
 * Exposes the configuration information for a given application.
 *
 * @author Dushyanth (Dee) Inguva - SmartCode LLC
 */
@ManagedResource(objectName = "org.openconfig:name=ConfigurationService", description = "Provides access to all OpenConfig Configurations configured on this server.")
public class ConfigurationService {

    private SessionFactory sessionFactory;
    private ApplicationService applicationService;
    private OpenMBeanApplicationTransformer applicationTransformer;

    @Required
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Required
    public void setApplicationTransformer(OpenMBeanApplicationTransformer applicationTransformer) {
        this.applicationTransformer = applicationTransformer;
    }

    @Required
    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * Gets the configurations for a given application.
     *
     * @param applicationName the name of the application to lookup the configurations with
     * @return All of the configurations for this application
     * @throws NoSuchApplicationFoundException
     *          If the application with the given name is not present
     */
    @ManagedOperation(description = "Gets the configuration with the given name.")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "name", description = "The name of the configuration.")})
    @DecorateWithSession
    public CompositeData getConfigurations(String applicationName) throws NoSuchApplicationFoundException {

        Application application = applicationService.findApplication(applicationName);
        CompositeData compositeData = applicationTransformer.transform(application);

        return compositeData;

    }
}
