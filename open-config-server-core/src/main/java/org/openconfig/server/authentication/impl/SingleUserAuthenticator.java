package org.openconfig.server.authentication.impl;

import org.openconfig.server.authentication.Authenticator;
import org.openconfig.server.authentication.Credentials;

import javax.security.auth.Subject;

import static org.springframework.util.Assert.isTrue;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class SingleUserAuthenticator implements Authenticator {

    private String username;

    private String password;

    public Subject authenticate(Credentials creditenals) {
        Credentials expectedCredentials = new Credentials(username, password);
        isTrue(creditenals.equals(expectedCredentials));
        return new Subject();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
