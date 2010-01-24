package org.openconfig.server.authentication;

import org.springframework.beans.factory.annotation.Required;

import static org.springframework.util.Assert.*;

import javax.management.remote.JMXAuthenticator;
import javax.security.auth.Subject;

/**
 * This class handles the delegation of authentication based upon the
 * clients authentication needs.
 *
 * TODO: The Authenticator needs to be resolved by a database lookup.
 * @author Richard L. Burton III - SmartCode LLC
 */
public class DelegateJMXAuthenticator implements JMXAuthenticator {

    /** The index of the username in the String array. */
    private static final int USERNAME = 0;

    /** The index of the password in the String array. */
    private static final int PASSWORD = 1;

    /** The Authenticator used to authenticate the request. */
    private Authenticator authenticator;

    /**
     * This method will validate the user credentials and then delegate the authentication
     * process to the supplied Authenticator.
     * @param userCredentials The credentials used to authenticate the client for.
     * @return The subject representing a successful login.
     * @throws IllegalArgumentException When the credentials are null or not of the right type.
     */
    public Subject authenticate(Object userCredentials) {
        notNull(userCredentials, "The provided credentials must not be null.");
        isTrue(userCredentials instanceof String[], "Expected type of String[] for parameter 'userCredentials'");
        
        String[] params = (String[]) userCredentials;
        String username = params[USERNAME];
        String password = params[PASSWORD];
        Credentials credentials = new Credentials(username, password);
        return authenticator.authenticate(credentials);
    }

    @Required
    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

}
