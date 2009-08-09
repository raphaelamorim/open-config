package org.openconfig.core;

/**
 * An enumeration of expected method types supported. 
 * @author Richard L. Burton III
 */
public enum Accessor {
    
    SETTER,
    GETTER,
    UNKNOWN;

    public static Accessor getAccessor(String property) {
        if (property.startsWith("get")) {
            return Accessor.GETTER;
        } else if (property.startsWith("set")) {
            return Accessor.SETTER;
        } else {
            return Accessor.UNKNOWN;
        }
    }

}
