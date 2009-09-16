package org.openconfig.core;

import java.lang.reflect.Method;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class Invocation {

    private Method method;

    private String property;

    public Invocation(Method method, String property) {
        this.method = method;
        this.property = property;
    }

    public Method getMethod() {
        return method;
    }

    public String getProperty() {
        return property;
    }
    
}
