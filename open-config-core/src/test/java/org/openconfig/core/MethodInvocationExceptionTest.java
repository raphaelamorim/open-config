package org.openconfig.core;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

/**
 * @author Richard L. Burton III
 */
public class MethodInvocationExceptionTest {

    /**
     * This test case maybe a little too much, but I want to ensure meaningful and consistent error messages.
     * @throws Exception
     */
    @Test
    public void verifyOfExceptionFormat() throws Exception {
        Person person = new Person();
        Method method = Person.class.getMethod("getName");
        MethodInvocationException exception = new MethodInvocationException(person, method);
        assertEquals("Method invoked is neither a setter or getter! org.openconfig.core.Person.getName", exception.getMessage());
    }
}
