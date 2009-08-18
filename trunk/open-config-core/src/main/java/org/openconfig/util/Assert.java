package org.openconfig.util;

/**
 * Simple assertion class.
 *
 * @author Dushyanth (Dee) Inguva
 */
public class Assert {

    public static boolean isTrue(boolean condition, String message, Object... placeholderValues) {
        if (!condition) {
            throw new IllegalArgumentException("AssertionFailed:" + String.format(message, placeholderValues));
        }
        return true;
    }

    /**
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
}
