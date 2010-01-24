package org.openconfig.server.domain;

import org.hibernate.annotations.MapKeyManyToMany;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

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

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "created_dt")
    private Date created = new Date();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapKeyManyToMany(joinColumns = @JoinColumn(name = "configuration_alias"))
    @JoinTable(name = "application_configurations", joinColumns = @JoinColumn(name = "application_id"),
            inverseJoinColumns = @JoinColumn(name = "configuration_id"))
    private Map<String, Configuration> configurations = new HashMap<String, Configuration>();

    /**
     * Adds a configuration with an alias to this application. If there is already a configuration with this alias,
     * it gets replaced.
     *
     * @param name          The local alias for this configuration.
     * @param configuration
     */
    public void addConfiguration(String name, Configuration configuration) {
        notNull(name);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Map<String, Configuration> getConfigurations() {
        return configurations;
    }

    /**
     * Gets the configuration with the given alias.
     *
     * @param alias
     * @return
     * @throws IllegalArgumentException if the
     */
    public Configuration getConfiguration(String alias) {
        isTrue(configurations.containsKey(alias));
        return configurations.get(alias);
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
