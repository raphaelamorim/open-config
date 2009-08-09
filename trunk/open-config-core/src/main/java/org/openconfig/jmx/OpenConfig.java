package org.openconfig.jmx;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import javax.management.openmbean.CompositeData;

/**
 * @author Richard L. Burton III
 */
@ManagedResource(objectName = "org.openconfig:name=jmxConfigurator",
        description = "OpenConfig JMX-managed bean")
public class OpenConfig implements OpenConfigMBean{

    @ManagedAttribute
    public void setValue(String application, String name, Object value) {
    }

    @ManagedAttribute
    public CompositeData getInitialState(String application) {
        return null;
    }

}
