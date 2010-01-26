package org.openconfig.server.repository;

import org.apache.log4j.Logger;
import org.openconfig.server.domain.Application;
import static org.openconfig.server.domain.Configuration.newConfiguration;
import static org.openconfig.server.domain.ConfigurationValue.newConfigurationValue;
import static org.openconfig.server.domain.ValueType.NUMERIC;
import static org.openconfig.server.domain.ValueType.STRING;
import org.openconfig.server.service.ApplicationService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.List;

/**
 * Seeds the database with sample application settings.
 *
 * @author Dushyanth (Dee) Inguva - SmartCode LLC
 */
public class DatabaseSeeder implements InitializingBean {

    private final Logger logger = Logger.getLogger(getClass());

    private ApplicationService applicationService;

    @Required
    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * Creates two applications and adds them to the database if they don't already exist.
     */
    public void afterPropertiesSet() {
        List<Application> applications = createSampleApplications();
        for (Application application : applications) {
            try {
                applicationService.findApplication(application.getName());
            }
            catch (NoSuchApplicationFoundException ignored) {
                applicationService.saveApplication(application);
            }
        }
    }

    private List<Application> createSampleApplications() {

        List<Application> applications = new ArrayList<Application>();
        Application firstApplication = createApplication("Twitter Web", "Website settings for Twitter");

        firstApplication.addConfiguration("messageDatabase",
                newConfiguration("messageDatabase", "Database Configuration of the twitter message store.",
                        newConfigurationValue("server", "TWITDB1", STRING), newConfigurationValue("port", "4000", NUMERIC),
                        newConfigurationValue("server", "TWITAUTH1", STRING), newConfigurationValue("port", "5000", NUMERIC)
                ));

        applications.add(firstApplication);

        Application secondApplication = createApplication("Twitter API", "API Application settings for Twitter");
        secondApplication.addConfiguration("authenticationService",
                newConfiguration("secureService", "Authentication Configuration of the twitter message store.",
                        newConfigurationValue("servername", "TWITAUTH1", STRING), newConfigurationValue("port", "5000", NUMERIC)
                ));

        applications.add(secondApplication);
        return applications;
    }

    private Application createApplication(String name, String description) {
        Application application = new Application();
        application.setName(name);
        application.setDescription(description);

        return application;
    }


}
