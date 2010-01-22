package org.openconfig.server.domain;

import java.util.*;
import java.io.Serializable;

import javax.persistence.*;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
@Entity
@Table(name="oc_application")
public class Application implements Serializable {

    @Id
    @Column(name = "application_name", length = 255)
    private String name;

    @Column(name = "created_dt")
    private Date created = new Date();

    @OneToMany(cascade= CascadeType.ALL, targetEntity = Configuration.class,fetch = FetchType.EAGER)
    private Set<Configuration> configurations = new HashSet<Configuration>();

    public void addConfiguration(Configuration configuration){
        configurations.add(configuration);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Set<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(Set<Configuration> configurations) {
        this.configurations = configurations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Application that = (Application) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Application{" +
                "name='" + name + '\'' +
                '}';
    }
    
}
