package org.openconfig.core;

/**
 * An enumeration of expected method types supported. 
 * @author Richard L. Burton III
 */
public enum Accessor {
    
    SETTER,
    GETTER,
    TOSTRING,
    EQUALS,
    HASHCODE,
    UNKNOWN;

    public static Accessor getAccessor(String property) {
        if (property.startsWith("get")) {
            return GETTER;
        } else if (property.startsWith("set")) {
            return SETTER;
        } else if (property.equals("toString")){
            return TOSTRING;
        } else if (property.equals("equals")){
            return EQUALS;
        } else if (property.equals("hashCode")){
            return HASHCODE;
        } else {
            return UNKNOWN;
        }
    }

}
