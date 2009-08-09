package org.openconfig.server.repository;

import java.util.List;

/**
 * @author Richard L. Burton III
 */
public class Application {

    private String name;

    List<AttributeDefinition> attributeDefinitions;

    List<ObjectDefinition> objectDefinitions;

    public List<AttributeDefinition> getAttributeDefinitions() {
        return attributeDefinitions;
    }

    public void setAttributeDefinitions(List<AttributeDefinition> attributeDefinitions) {
        this.attributeDefinitions = attributeDefinitions;
    }

    public void setObjectDefinitions(List<ObjectDefinition> objectDefinitions) {
        this.objectDefinitions = objectDefinitions;
    }

    public List<ObjectDefinition> getObjectDefinitions() {
        return objectDefinitions;
    }

    public void addAttributeDefinition(AttributeDefinition attributeDefinition) {
        attributeDefinitions.add(attributeDefinition);
    }

    public void addObjectDefinition(ObjectDefinition objectDefinition) {
        this.objectDefinitions.add(objectDefinition);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
