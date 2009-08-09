package org.openconfig.jmx;

import javax.management.JMX;
import javax.management.ObjectName;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXConnector;
import java.io.IOException;

/**
 * @author Richard L. Burton III
 */
public class JmxClient {

    private String serviceURL = "service:jmx:rmi:///jndi/rmi://:9999/jmxrmi";

    private String jmxObjectName = "org.openconfig:name=jmxConfigurator";

    private JMXConnector jmxConnector;
    private MBeanServerConnection connection;
    private ObjectName objectName;

    public JmxClient(String serviceURL, String jmxObjectName) {
        this.serviceURL = serviceURL;
        this.jmxObjectName = jmxObjectName;
    }

    public void connect() {
        try {
            JMXServiceURL jmxServiceURL = new JMXServiceURL(serviceURL);
            jmxConnector = JMXConnectorFactory.connect(jmxServiceURL, null);
            connection = jmxConnector.getMBeanServerConnection();
            objectName = new ObjectName(jmxObjectName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void jmx() throws IOException, MalformedObjectNameException {
        OpenConfigMBean configurator = JMX.newMXBeanProxy(connection, objectName, OpenConfigMBean.class, true);
        System.out.println("configurator.getValue(\"test\"); = " + configurator.getInitialState("test"));
    }

    public void setServiceURL(String serviceURL) {
        this.serviceURL = serviceURL;
    }

    public String getServiceURL() {
        return serviceURL;
    }

    public String getJmxObjectName() {
        return jmxObjectName;
    }

    public void setJmxObjectName(String jmxObjectName) {
        this.jmxObjectName = jmxObjectName;
    }

    @Override
    protected void finalize() throws Throwable {
        jmxConnector.close();
    }
}
