package org.openconfig.jmx;

import javax.management.*;
import javax.management.remote.JMXConnector;

import static javax.management.remote.JMXConnector.CREDENTIALS;
import static org.openconfig.util.Assert.notNull;

import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class JmxClient {

    private static final String OPENCONFIG_OBJECTNAME = "org.openconfig:name=ConfigurationService";

    private JMXConnector connector = null;

    private JMXServiceURL jmxServiceURL;

    private String serviceURL;

    private String username;

    private String password;

    public void connect() throws IOException {
        notNull(serviceURL, "The 'serviceURL' property can not be null.");
        notNull(username, "The 'username' property can not be null.");
        notNull(password, "The 'password' property can not be null.");

        Map<String, Object> environment = new HashMap<String, Object>();
        environment.put(CREDENTIALS, new String[]{username, password});
        jmxServiceURL = new JMXServiceURL(serviceURL);
        connector = JMXConnectorFactory.connect(jmxServiceURL, environment);
    }

    public MBeanInfo getOpenConfigMBean() throws MalformedObjectNameException, IOException, IntrospectionException, InstanceNotFoundException, ReflectionException {
        ObjectName object = new ObjectName(OPENCONFIG_OBJECTNAME);
        MBeanServerConnection connection = connector.getMBeanServerConnection();
        return connection.getMBeanInfo(object);
    }

    public void close() {
        try {
            if (connector != null)
                connector.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setServiceURL(String serviceURL) {
        this.serviceURL = serviceURL;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
