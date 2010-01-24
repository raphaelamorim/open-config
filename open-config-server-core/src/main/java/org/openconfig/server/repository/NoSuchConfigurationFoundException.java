package org.openconfig.server.repository;

/**
 * Thrown when the Configuration element is not found in the repository.
 * @author Richard L. Burton III - SmartCode LLC
 */
public class NoSuchConfigurationFoundException extends RuntimeException {

    private String name;

    public NoSuchConfigurationFoundException(String message, String name) {
        super(message);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
