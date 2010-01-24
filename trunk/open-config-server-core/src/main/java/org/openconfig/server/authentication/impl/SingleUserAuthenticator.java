package org.openconfig.server.authentication.impl;

import org.openconfig.server.authentication.Authenticator;
import org.openconfig.server.authentication.Credentials;

import javax.security.auth.Subject;

import static org.springframework.util.Assert.isTrue;

/**
 * This class is used to allow only a single username and password to be used.
 * The purpose of this class is only for testing purposes since there's no real
 * world usage for such a class.
 * @author Richard L. Burton III - SmartCode LLC
 */
public class SingleUserAuthenticator implements Authenticator {

    /** The username of the account to be authenticated. */
    private String username;

    /** The password for the account. */
    private String password;

    /**
     * This method simple checks that the credentials
     * @param credentials The clients credentials used for authentication.
     * @return A newly created Subject object.
     */
    public Subject authenticate(Credentials credentials) {
        Credentials expectedCredentials = new Credentials(username, password);
        isTrue(credentials.equals(expectedCredentials));
        return new Subject();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
