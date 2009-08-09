package org.openconfig.ioc.config;

/**
 * @author Richard L. Burton III
 */
public interface OpenConfigConfiguration {

    void process(String file) throws Exception;

    boolean hasClass(String alias);

    Class getClass(String name);
}
