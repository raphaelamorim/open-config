package org.openconfig.core;

import java.lang.reflect.Method;

/**
 * Thrown when the method doesn't follow the Java Bean specification for getters
 * and setters.
 *
 * @author Richard L. Burton III
 */
public class MethodInvocationException extends RuntimeException {

    public MethodInvocationException(Object source, Method method) {
        super("Method invoked is neither a setter or getter! " + normalize(source, method));
    }

    static String normalize(Object source, Method method) {
        Class clazz = source.getClass();
        StringBuilder invocation = new StringBuilder(clazz.getName())
                .append('.')
                .append(method.getName());
        return invocation.toString();
    }
}
