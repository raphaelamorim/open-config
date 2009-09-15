package org.openconfig.example.commandline;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public interface DatabaseConfigurator {

    Database getDatabase();

    int getPoolSize();
}
