package org.openconfig.configurators;

import org.openconfig.MutableConfigurator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Richard L. Burton III
 */
public class MapConfigurator implements MutableConfigurator {

    Map<String, Object> cache = new HashMap<String, Object>();

    public void setValue(String name, Object value) {
        cache.put(name, value);
    }

    public Object getValue(String key) {
        Object value = cache.get(key);
        if (value == null) {
            value = key;
            setValue(key, key);
        }
        return value;
    }
}
