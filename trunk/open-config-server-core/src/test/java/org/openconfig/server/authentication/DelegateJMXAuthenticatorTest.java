package org.openconfig.server.authentication;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.mockito.Matchers;

import static org.mockito.Mockito.*;

import javax.security.auth.Subject;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class DelegateJMXAuthenticatorTest {

    private DelegateJMXAuthenticator delegateJMXAuthenticator;

    private Authenticator authenticator;

    private Object parameters = new String[]{"rburton", "test"};

    @Test
    public void verifyStringArray() {
        when(authenticator.authenticate(Matchers.<Credentials>any())).thenReturn(new Subject());
        Subject subject = delegateJMXAuthenticator.authenticate(parameters);
        assertNotNull(subject);
    }

    @Test
    public void verifyCredentialsArePassed() {
        Credentials credentials = new Credentials("rburton", "test");
        when(authenticator.authenticate(credentials)).thenReturn(new Subject());
        Subject subject = delegateJMXAuthenticator.authenticate(parameters);
        assertNotNull(subject);
    }

    @Test
    public void verifyInvalidParameterType() {
        when(authenticator.authenticate(Matchers.<Credentials>any())).thenReturn(new Subject());
        try {
            Object parameters = "invalid";
            delegateJMXAuthenticator.authenticate(parameters);
            fail("Expected IllegalArgumentException");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void verifyNullParameter() {
        when(authenticator.authenticate(Matchers.<Credentials>any())).thenReturn(new Subject());
        try {
            delegateJMXAuthenticator.authenticate(null);
            fail("Expected IllegalArgumentException");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Before
    public void setup() {
        delegateJMXAuthenticator = new DelegateJMXAuthenticator();
        authenticator = mock(Authenticator.class);
        delegateJMXAuthenticator.setAuthenticator(authenticator);
    }
}
