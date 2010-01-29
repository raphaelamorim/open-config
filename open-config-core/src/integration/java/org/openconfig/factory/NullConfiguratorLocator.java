package org.openconfig.factory;

import java.net.URL;

/**
 * Always misses to locate the configuration.
 *
 * @author Dushyanth (Dee) Inguva - SmartCode LLC
 */
public class NullConfiguratorLocator implements ConfigurationLocator {
    
    public URL locate() {
        return null;
    }
}
