package org.openconfig.jmx.builder;

import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class CompositeTypeBuilder {

    private String name;
    private String description;

    private List<String> attributeNames = new LinkedList<String>();
    private List<OpenType> attributeOpenType = new LinkedList<OpenType>();

    public CompositeTypeBuilder(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public CompositeTypeBuilder addType(String name, OpenType type) {
        attributeNames.add(name);
        attributeOpenType.add(type);
        return this;
    }

    public CompositeType build() throws OpenDataException {
        return new CompositeType(name
                , description
                , attributeNames.toArray(new String[]{})
                , attributeNames.toArray(new String[]{})
                , attributeOpenType.toArray(new OpenType[]{}));
    }
}
