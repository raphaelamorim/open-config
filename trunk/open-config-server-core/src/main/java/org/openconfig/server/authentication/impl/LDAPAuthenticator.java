package org.openconfig.server.authentication.impl;

import org.openconfig.server.authentication.Authenticator;
import org.openconfig.server.authentication.Credentials;

import javax.security.auth.Subject;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class LDAPAuthenticator implements Authenticator {
    public Subject authenticate(Credentials creditenals) {
        return null;
    }
}
