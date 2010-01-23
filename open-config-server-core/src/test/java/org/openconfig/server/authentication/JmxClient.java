package org.openconfig.server.authentication;

import org.springframework.beans.factory.annotation.Required;

import static org.springframework.util.Assert.*;

import javax.management.*;
import javax.management.remote.JMXConnector;

import static javax.management.remote.JMXConnector.CREDENTIALS;

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
        notNull(serviceURL);
        notNull(username);
        notNull(password);

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

    @Required
    public void setServiceURL(String serviceURL) {
        this.serviceURL = serviceURL;
    }

    @Required
    public void setUsername(String username) {
        this.username = username;
    }

    @Required
    public void setPassword(String password) {
        this.password = password;
    }
}
