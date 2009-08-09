package org.openconfig.core;

import junit.framework.TestCase;

import java.lang.reflect.Method;

/**
 * @author Richard L. Burton III
 */
public class MethodInvocationExceptionTestCase extends TestCase {

    /**
     * This test case maybe a little too much, but I want to ensure meaningful and consistant error messages.
     * @throws Exception
     */
    public void testFormat() throws Exception {
        Person person = new Person();
        Method method = Person.class.getMethod("getName");
        MethodInvocationException exception = new MethodInvocationException(person, method);
        assertEquals("Method invoked is neither a setter or getter! org.openconfig.core.Person.getName", exception.getMessage());
    }
}
