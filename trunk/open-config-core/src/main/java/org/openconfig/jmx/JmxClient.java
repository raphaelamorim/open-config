package org.openconfig.jmx;

import org.apache.log4j.Logger;

import javax.management.*;
import javax.management.remote.JMXConnector;

import static javax.management.remote.JMXConnector.CREDENTIALS;
import static org.openconfig.util.Assert.notNull;

import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;

import static java.util.Collections.singletonMap;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class JmxClient {

    private static Logger LOGGER = Logger.getLogger(JmxClient.class);

    private static final String OPENCONFIG_OBJECTNAME = "org.openconfig:name=ConfigurationService";

    private static final String GETCONFIGURATION_METHOD = "getConfigurations";

    private ObjectName objectName;

    private JMXConnector connector;

    private String serviceURL;

    private String username;

    private String password;

    private MBeanServerConnection connection;

    private String[] GET_CONFIGURATION_TYPES = {String.class.getName()};

    /**
     * Connects the client the the JMX server.
     *
     * @throws IllegalStateException    If the client is already connected to the server.
     * @throws IllegalArgumentException If the service url, username or password are null.
     */
    public void connect() {
        notNull(serviceURL, "The 'serviceURL' property can not be null.");
        notNull(username, "The 'username' property can not be null.");
        notNull(password, "The 'password' property can not be null.");

        String[] credentials = {username, password};
        try {
            JMXServiceURL jmxServiceURL = new JMXServiceURL(serviceURL);
            LOGGER.debug("Preparing to connect to '" + serviceURL + "' server.");
            connector = JMXConnectorFactory.connect(jmxServiceURL, singletonMap(CREDENTIALS, credentials));
            objectName = new ObjectName(OPENCONFIG_OBJECTNAME);
            connection = connector.getMBeanServerConnection();
            registerListeners();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MBeanInfo getOpenConfigMBean() throws MalformedObjectNameException, IOException, IntrospectionException, InstanceNotFoundException, ReflectionException {
        return connection.getMBeanInfo(objectName);
    }

    public Object getConfigurations(String application) throws Exception {
        Object[] arguments = {application};
        LOGGER.debug("Obtaining the Configuration information for application '" + application + "'");
        return connection.invoke(objectName, GETCONFIGURATION_METHOD, arguments, GET_CONFIGURATION_TYPES);
    }

    /**
     * Registers the JMX Notification listener for changes from the server.
     */
    protected void registerListeners() {
        NotificationFilterSupport filter = new NotificationFilterSupport();
        ChangeStateNotificationListener listener = new ChangeStateNotificationListener();
        try {
            LOGGER.debug("Registering the ChangeStateNotificationListener listener.");
            connection.addNotificationListener(objectName, listener, filter, null);
        } catch (InstanceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            if (connector != null) {
                LOGGER.debug("Closing the JmxClient off.");
                connector.close();
            }
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
