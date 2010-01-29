package org.openconfig.factory;

import java.net.URL;

/**
 * @author Dushyanth (Dee) Inguva - SmartCode LLC
 */
public class NoOpConfiguratorLocator implements ConfigurationLocator {
    public URL locate() throws NoConfigurationFileFoundException {
        return null;
    }
}
