package org.openconfig.server.authentication;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.openconfig.jmx.JmxClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.management.MBeanInfo;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/org/openconfig/server/authentication/jmx-authentication-context.xml")
public class JmxAuthenticationTest {

    private JmxClient client;

    @Test
    public void verifyAuthentication() throws Exception {
        client.setUsername("rburton");
        client.setPassword("test");
        client.connect();
        MBeanInfo info = client.getOpenConfigMBean();
        assertNotNull(info);
        client.close();
    }

    @Test
    public void verifyFailedAuthentication() throws Exception {
        client.setUsername("rburton");
        client.setPassword("peoplesuck");

        try {
            client.connect();
            fail("Authentication should have failed");
        } catch (IllegalArgumentException e) {

        } catch (Exception e) {
            fail("Wrong Exception type");
        } finally {
            client.close();
        }
    }

    @Before
    public void setup() {
        client = new JmxClient();
        client.setServiceURL("service:jmx:rmi://localhost/jndi/rmi://localhost:1099/openConfigConnector");
    }

}
