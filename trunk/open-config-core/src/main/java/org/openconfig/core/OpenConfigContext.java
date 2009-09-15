package org.openconfig.core;

/**
 * An abstraction of the container that OpenConfig is running within. This allows
 * for testable logic that abstracts the J2EE API and other environmental specific
 * APIs.
 *
 * @author Richard L. Burton III
 */
public interface OpenConfigContext extends java.io.Serializable{

    String getParameter(String name);

}
