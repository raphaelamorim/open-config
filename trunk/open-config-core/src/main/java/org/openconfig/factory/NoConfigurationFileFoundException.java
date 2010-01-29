package org.openconfig.factory;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class NoConfigurationFileFoundException extends Exception {

    public NoConfigurationFileFoundException(String fileName, String[] extensions) {
        this(String.format("No configuration file for %s with extensions of [%s]", fileName, toString(extensions)));
    }

    public NoConfigurationFileFoundException(String message) {
        super(message);
    }

    private static String toString(String[] extensions){
        String ret = "";
        for(String extension : extensions){
            ret += extension + " ";
        }
        return ret;
    }
}
