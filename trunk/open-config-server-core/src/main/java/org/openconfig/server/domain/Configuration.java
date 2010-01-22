package org.openconfig.server.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
// TODO: Define ApplicationTemplate, ConfigurationTemplate so people can define a template and reuse them while creating
// configuration and application. Also, think about defining department/company standards when coming to properties.
@Entity
@Table(name = "oc_configuration")
public class Configuration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "configuration_id")
    private Long id;

    @Column(name = "configuration_name", length = 255)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = ConfigurationValue.class, fetch = FetchType.EAGER)
    private Set<ConfigurationValue> values = new HashSet<ConfigurationValue>();

    public void addConfigurationValue(ConfigurationValue configurationValue) {
        values.add(configurationValue);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ConfigurationValue> getValues() {
        return values;
    }

    public void setValues(Set<ConfigurationValue> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Configuration that = (Configuration) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

}
