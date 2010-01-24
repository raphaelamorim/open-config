package org.openconfig.server.repository;

/**
 * Thrown when an application is not found in the Repository.
 * @author Richard L. Burton III - SmartCode LLC
 */
public class NoSuchApplicationFoundException extends RuntimeException {

    /** The application name that was not found. */
    private String name;

    public NoSuchApplicationFoundException(String message, String name) {
        super(message);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
