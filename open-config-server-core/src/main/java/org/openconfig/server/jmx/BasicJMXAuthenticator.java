package org.openconfig.server.jmx;

import javax.management.remote.JMXAuthenticator;
import javax.security.auth.Subject;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class BasicJMXAuthenticator implements JMXAuthenticator {
    @Override
    public Subject authenticate(Object o) {
        System.out.println("o = " + o);
        return null;
    }
}
