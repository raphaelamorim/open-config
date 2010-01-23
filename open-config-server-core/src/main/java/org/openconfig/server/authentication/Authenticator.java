package org.openconfig.server.authentication;

import javax.security.auth.Subject;

/**
 * This interface defines the logic used for authenticating clients connecting
 * to the OpenConfig Server abstracting the protocol in which they communicate
 * over.
 * @author Richard L. Burton III - SmartCode LLC
 */
public interface Authenticator {

    /**
     * Authenticates the client based upon the Credentials that were submitted
     * in the request.
     * @param creditenals The clients credentials used for authentication.
     * @return The JAAS Subject reflecting a successful authentication.
     */
    Subject authenticate(Credentials creditenals);
    
}
