package org.openconfig.example.domain;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class Administrator {

    private String name;

    private Long id;

    public Administrator(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public Administrator() {
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}
