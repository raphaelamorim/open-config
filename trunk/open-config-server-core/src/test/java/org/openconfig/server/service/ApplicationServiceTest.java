package org.openconfig.server.service;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.openconfig.server.domain.Application;
import org.openconfig.server.domain.Configuration;
import org.openconfig.server.repository.ApplicationRepository;

import java.util.HashMap;

/**
 * @author Dushyanth (Dee) Inguva - SmartCode LLC
 */
public class ApplicationServiceTest {

    private ApplicationService applicationService = new ApplicationService();
    private ApplicationRepository applicationRepository = mock(ApplicationRepository.class);
    private Application application = mock(Application.class);

    @Before
    public void setup() {
        applicationService.setApplicationRepository(applicationRepository);
    }

    @Test
    public void testFindConfigurations() {
        String appName = "D'OH";
        when(applicationRepository.findByName(appName)).thenReturn(application);
        when(application.getConfigurations()).thenReturn(new HashMap<String, Configuration>());

        applicationService.findConfigurationsByApplication(appName);
    }
}