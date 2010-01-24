package org.openconfig.jmx.builder;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class CompositeDataBuilder {

    private CompositeTypeBuilder compositeTypeBuilder;

    private Map<String, Object> attributes = new HashMap<String, Object>();

    private CompositeDataBuilder compositeDataBuilder;

    public CompositeDataBuilder(String name, String description) {
        compositeTypeBuilder = new CompositeTypeBuilder(name, description);
    }

    public CompositeDataBuilder addAttribute(String name, Object value, OpenType openType) {
        if (compositeDataBuilder != null) {
            compositeDataBuilder.addAttribute(name, value, openType);
        } else {
            attributes.put(name, value);
            compositeTypeBuilder.addType(name, openType);
        }
        return this;
    }

    public CompositeDataBuilder addChildCompositeData(String name, String description) {
        if (compositeDataBuilder != null) {
            addInternalCompositeData();
        }
        compositeDataBuilder = new CompositeDataBuilder(name, description);
        return this;
    }

    private void addInternalCompositeData() {
        if (compositeDataBuilder != null) {
            CompositeData compositeData = compositeDataBuilder.build();
            attributes.put(compositeDataBuilder.getCompositeTypeBuilder().getName(), compositeData);
            compositeTypeBuilder.addType(compositeDataBuilder.getCompositeTypeBuilder().getName(), compositeData.getCompositeType());
        }
    }

    public CompositeTypeBuilder getCompositeTypeBuilder() {
        return compositeTypeBuilder;
    }

    public CompositeData build() {
        try {
            addInternalCompositeData();
            CompositeType compositeType = compositeTypeBuilder.build();
            return new CompositeDataSupport(compositeType, attributes);
        } catch (Exception e) {
            throw new RuntimeException(e); // TODO: Find something better throw.
        }
    }
}
