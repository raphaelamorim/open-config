package org.openconfig.factory;

import java.net.URL;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public interface ConfigurationLocator {

    URL locate() throws NoConfigurationFileFoundException;

}
