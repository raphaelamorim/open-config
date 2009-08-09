package org.openconfig.server.repository;

import java.util.List;

/**
 * @author Richard L. Burton III
 */
public class ObjectDefinition {

    private String name;

    private List<AttributeDefinition> attributeDefinitions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AttributeDefinition> getAttributeDefinitions() {
        return attributeDefinitions;
    }

    public void setAttributeDefinitions(List<AttributeDefinition> attributeDefinitions) {
        this.attributeDefinitions = attributeDefinitions;
    }

    public void addAttributeDefinition(AttributeDefinition attributeDefinition) {
        attributeDefinitions.add(attributeDefinition);
    }
}
