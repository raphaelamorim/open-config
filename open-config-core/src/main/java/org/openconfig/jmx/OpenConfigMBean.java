package org.openconfig.jmx;

import javax.management.openmbean.CompositeData;

/**
 * @author Richard L. Burton III
 */
public interface OpenConfigMBean {

    CompositeData getInitialState(String application);

    void setValue(String application, String name, Object value);
}
