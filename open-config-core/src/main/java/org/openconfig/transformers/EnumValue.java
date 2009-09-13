package org.openconfig.transformers;

/**
 * A wrapper that encapsulates all of the required data used
 * in transforming the return value into an Enum.
 *
 * @author Richard L. Burton III - SmartCode LLC
 */
public class EnumValue {

    private Class enumClass;

    private Object value;

    public EnumValue(Class clazz, Object returnvalue) {
        this.enumClass = clazz;
        this.value = returnvalue;
    }

    public Class getEnumClass() {
        return enumClass;
    }

    public Object getValue() {
        return value;
    }
}
