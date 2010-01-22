package org.openconfig.server.domain;

import static org.openconfig.server.domain.ValueType.*;
import static org.openconfig.util.Assert.notNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
@Entity
@Table(name = "oc_configuration_value")
public class ConfigurationValue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_node")
    private ConfigurationValue parent;

    @OneToMany(mappedBy = "parent")
    private Set<ConfigurationValue> children = new HashSet<ConfigurationValue>();

    @Column(name = "config_name", length = 255)
    private String name;

    @Column(name = "config_type", length = 255)
    private String type;

    @Column(name = "int_value")
    private int intValue;

    @Column(name = "double_value")
    private double doubleValue;

    @Column(name = "string_value")
    private String stringValue;

    @Column(name = "boolean_value")
    private boolean booleanValue;

    @Column(name = "float_value")
    private float floatValue;

    @Column(name = "char_value", length = 1)
    private char charValue;

    @Column(name = "short_value")
    private short shortValue;

    public ConfigurationValue() {
    }

    public Set<ConfigurationValue> getChildren() {
        return children;
    }

    public void setChildren(Set<ConfigurationValue> children) {
        this.children = children;
    }

    public void addChild(ConfigurationValue configurationValue) {
        configurationValue.setParent(this);
        children.add(configurationValue);
    }

    public void setName(String name) {
        this.name = name;

    }

    public ConfigurationValue getParent() {
        return parent;
    }

    public void setParent(ConfigurationValue parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        type = INT.toString();
        this.intValue = intValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        type = DOUBLE.toString();
        this.doubleValue = doubleValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        type = STRING.toString();
        this.stringValue = stringValue;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        type = BOOLEAN.toString();
        this.booleanValue = booleanValue;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }

    public char getCharValue() {
        return charValue;
    }

    public void setCharValue(char charValue) {
        type = CHAR.toString();
        this.charValue = charValue;
    }

    public short getShortValue() {
        return shortValue;
    }

    public void setShortValue(short shortValue) {
        type = SHORT.toString();
        this.shortValue = shortValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfigurationValue that = (ConfigurationValue) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (parent != null ? !parent.equals(that.parent) : that.parent != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = parent != null ? parent.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    /**
     * Creates a child configuration value, adds it to the specified parent and returns the newly created child.
     *
     * @param parent
     * @param name the name of the child
     * @param value the value of the child
     * @param valueType the type of the value of the child
     * @return
     */
    public ConfigurationValue newChildConfigurationValue(Configuration parent, String name, Object value, ValueType valueType) {
        ConfigurationValue child = newConfigurationValue(name, value, valueType);
        addChild(child);
        return child;
    }


    /**
     * Convenience method that takes a value and a valueType(hint) and creates a ConfigurationValue object.
     *
     * @param name
     * @param value     a non null value whose type is defined by: {@link org.openconfig.server.domain.ValueType}
     * @param valueType the corresponding value type
     * @return
     */
    public static ConfigurationValue newConfigurationValue(String name, Object value, ValueType valueType) {
        notNull(name, "Value with name %s and of type %s cannot be null", name, valueType);

        ConfigurationValue configurationValue = new ConfigurationValue();
        configurationValue.setName(name);

        switch (valueType) {

            case INT:
                configurationValue.setIntValue((Integer) value);
                break;
            case DOUBLE:
                configurationValue.setDoubleValue((Double) value);
                break;
            case STRING:
                configurationValue.setStringValue((String) value);
                break;
            case BOOLEAN:
                configurationValue.setBooleanValue((Boolean) value);
                break;
            case FLOAT:
                configurationValue.setFloatValue((Float) value);
                break;
            case CHAR:
                configurationValue.setCharValue((Character) value);
                break;
            case SHORT:
                configurationValue.setShortValue((Short) value);
                break;
            default:
                throw new IllegalArgumentException("Unknown data type: " + value.getClass().getName() + " for value: " + value + ". It has to be one of: "
                        + Arrays.toString(ValueType.values()));

        }
        return configurationValue;
    }
}
