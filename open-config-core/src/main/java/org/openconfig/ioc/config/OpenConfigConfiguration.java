package org.openconfig.ioc.config;

import java.net.URL;

/**
 * @author Richard L. Burton III
 */
public interface OpenConfigConfiguration {

    void process(URL file);

    boolean hasClass(String alias);

    /**
     * Loads and gets the extension point with the corresponding mapping name. For a list of valid mapping names, look at TODO
     * @param name
     * @return returns null if the extension point could not be found
     */
    Class<?> getClass(String name);
}
