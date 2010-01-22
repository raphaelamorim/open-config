package org.openconfig.server.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * An Application is a logical grouping of Configurations. A typical application may be comprised of multiple configurations
 * each of them defining settings for a module of the application.
 * <p/>
 * Even though a Configuration has a globally unique name within the system, in an Application, they can be given locally unique
 * names. These names work like aliases to the configuration which are unique only inside the application.
 *
 * @author Dushyanth (Dee) Inguva - SmartCode LLC
 * @author Richard L. Burton III - SmartCode LLC
 */
@Entity
@Table(name = "oc_application")
public class Application implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "application_name", length = 255, unique = true)
    private String name;

    @Column(name = "created_dt")
    private Date created = new Date();
   /*
    // TODO: Dee - Still have to test this
    @MapKey(name = "configuration_alias")
    @JoinTable(joinColumns = @JoinColumn(name = "application_id"), name = "application_configuration")
    @ManyToMany(cascade = CascadeType.ALL, targetEntity = Configuration.class, fetch = FetchType.LAZY, mappedBy = "configuration_id")
    */
    transient
    private Map<String, Configuration> configurations = new HashMap<String, Configuration>();

    public void addConfiguration(String name, Configuration configuration) {
        configurations.put(name, configuration);
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

    public Map<String, Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(Map<String, Configuration> configurations) {
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
