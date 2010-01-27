package org.openconfig.server.domain;

import static org.openconfig.server.domain.ValueType.*;
import static org.openconfig.util.Assert.isTrue;
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

    @Column(name = "descripton", length = 255)
    private String description;

    @Column(name = "config_type", length = 255)
    private ValueType valueType;

    @Column(name = "numeric_value")
    private String numericValue;

    @Column(name = "string_value")
    private String stringValue;

    @Column(name = "boolean_value")
    private boolean booleanValue;

    @Column(name = "secure_value")
    private String secureValue;

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

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        valueType = STRING;
        this.stringValue = stringValue;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        valueType = BOOLEAN;
        this.booleanValue = booleanValue;
    }

    public String getNumericValue() {
        return numericValue;
    }

    public void setNumericValue(String numericValue) {
        this.numericValue = numericValue;
        valueType = NUMERIC;
    }

    public String getSecureValue() {
        return secureValue;
    }

    public void setSecureValue(String secureValue) {
        this.secureValue = secureValue;
        valueType = SECURE;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
     * TODO: Move this logic into it's own class.
     * Creates a child configuration value, adds it to the specified parent and returns the newly created child.
     *
     * @param parent
     * @param name      the name of the child
     * @param value     the value of the child
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
        isTrue(valueType != PARENT, "Cannot assign value %s to key as the key is defined as a parent node", name, value);

        ConfigurationValue configurationValue = new ConfigurationValue();
        configurationValue.setName(name);

        switch (valueType) {

            case NUMERIC:
                configurationValue.setNumericValue((String) value);
                break;
            case SECURE:
                configurationValue.setSecureValue((String) value);
                break;
            case STRING:
                configurationValue.setStringValue((String) value);
                break;
            case BOOLEAN:
                configurationValue.setBooleanValue((Boolean) value);
                break;
            default:
                throw new IllegalArgumentException("Unknown data type: " + value.getClass().getName() + " for value: " + value + ". It has to be one of: "
                        + Arrays.toString(ValueType.values()));

        }
        return configurationValue;
    }

    /**
     * Convenience method that takes a value and a valueType(hint) and creates a ConfigurationValue object.
     *
     * @param name
     * @param value
     * @param children  the children configuration values that will be added to this configuration value.
     * @param valueType the corresponding value type
     * @return
     * @see #newConfigurationValue(String, Object, ValueType)
     */
    public static ConfigurationValue newConfigurationValue(String name, Object value, ValueType valueType, ConfigurationValue... children) {
        ConfigurationValue configurationValue = newConfigurationValue(name, value, valueType);

        for (ConfigurationValue child : children) {
            configurationValue.addChild(child);
        }
        return configurationValue;
    }
}
