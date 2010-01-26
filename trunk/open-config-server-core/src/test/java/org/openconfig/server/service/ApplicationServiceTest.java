package org.openconfig.server.service;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openconfig.server.domain.Application;
import org.openconfig.server.domain.Configuration;
import org.openconfig.server.repository.ApplicationRepository;

import java.util.HashMap;

/**
 * @author Dushyanth (Dee) Inguva - SmartCode LLC
 */
public class ApplicationServiceTest {

    @Mock
    private ApplicationService applicationService;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private Application application;

    @Test
    public void testFindConfigurations() {
        String appName = "D'OH";
        when(applicationRepository.findByName(appName)).thenReturn(application);
        when(application.getConfigurations()).thenReturn(new HashMap<String, Configuration>());

        applicationService.findConfigurationsByApplication(appName);
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        applicationService.setApplicationRepository(applicationRepository);
    }
     
}