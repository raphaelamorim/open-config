package integration.org.openconfig.server.authentication;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.openconfig.server.authentication.JmxClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.management.MBeanInfo;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/integration/org/openconfig/server/authentication/jmx-authentication-context.xml")
public class JmxAuthenticationTest {

    @Test
    public void verifyAuthentication() throws Exception {
        JmxClient client = new JmxClient();
        client.setServiceURL("service:jmx:rmi://localhost/jndi/rmi://localhost:1099/openConfigConnector");
        client.setUsername("rburton");
        client.setPassword("test");
        client.connect();
        MBeanInfo info = client.getOpenConfigMBean();
        assertNotNull(info);
        client.close();
    }

    @Test
    public void verifyFailedAuthentication() throws Exception {
        JmxClient client = new JmxClient();
        client.setServiceURL("service:jmx:rmi://localhost/jndi/rmi://localhost:1099/openConfigConnector");
        client.setUsername("rburton");
        client.setPassword("peoplesuck");

        try {
            client.connect();
            fail("Authentication should have failed");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

}
