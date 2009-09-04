package org.openconfig.util;

/**
 * Simple assertion class for usage inside openconfig. Not recommended for general use.
 *
 * @author Dushyanth (Dee) Inguva
 */
public class Assert {

    // TODO javadoc me
    public static boolean isTrue(boolean condition, String message, Object... placeholderValues) {
        if (!condition) {
            throw new IllegalArgumentException("AssertionFailed:" + String.format(message, placeholderValues));
        }
        return true;
    }

    /**
     * TODO javadoc me
     *
     * @param nonNullObject
     * @param message
     * @param placeholderValues
     * @return
     */
    public static boolean notNull(Object nonNullObject, String message, Object... placeholderValues) {
        if (nonNullObject == null) {
            throw new IllegalArgumentException("AssertionFailed:" + String.format(message, placeholderValues));
        }
        return true;
    }

    /**
     * Asserts that the passed String is not null and has non white space characters.
     *
     * @param aString the string to check
     * @param message
     * @param placeholderValues
     * @return
     * @throws IllegalArgumentException if aString is null, empty or contains just white spaces
     */
    public static boolean hasLength(String aString, String message, Object... placeholderValues) {
        return isTrue(aString != null, message, placeholderValues) && isTrue(aString.trim().length() > 0, message, placeholderValues) ;
    }
}
