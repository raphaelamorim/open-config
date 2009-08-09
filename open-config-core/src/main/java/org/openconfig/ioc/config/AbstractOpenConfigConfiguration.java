package org.openconfig.ioc.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Richard L. Burton III
 */
public abstract class AbstractOpenConfigConfiguration implements OpenConfigConfiguration {

    protected Map<String, String> extentionPoints = new HashMap<String, String>();

    public boolean hasClass(String alias) {
        return extentionPoints.containsKey(alias);
    }

    public Class getClass(String name) {
        String clazz = extentionPoints.get(name);
        try {
            return Class.forName(clazz);
        } catch (ClassNotFoundException cnfe) {
            throw new RuntimeException(cnfe);
        }
    }

    protected void addExtentionPoint(String name, String value) {
        extentionPoints.put(name, value);
    }
}
