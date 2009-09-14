package org.openconfig;

import org.apache.log4j.Logger;
import org.openconfig.util.Assert;

/**
 * This class models the environment in which OpenConfig is running in.
 *
 * @author Richard L. Burton III
 */
public class Environment implements java.io.Serializable {

    private static final Logger LOGGER = Logger.getLogger(Environment.class);

    public static final String LOCAL_ENVIRONMENT = "local";

    public static final Environment LOCAL = new Environment(LOCAL_ENVIRONMENT);

    private final String name;

    public Environment(String name) {
        Assert.hasLength(name, "Unable to determine openconfig environment");
        this.name = name;
        LOGGER.info("The active environment for OpenConfig is " + name);
    }

    public String getName() {
        return name;
    }

    /**
     * @return true if the current environment is local (development), false otherwise
     */
    public boolean isLocal() {
        return this.equals(LOCAL);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Environment that = (Environment) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
