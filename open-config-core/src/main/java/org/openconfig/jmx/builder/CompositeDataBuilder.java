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

    public CompositeDataBuilder(String name, String description) {
        compositeTypeBuilder = new CompositeTypeBuilder(name, description);
    }

    public void addAttribute(String name, Object value, OpenType openType) {
        attributes.put(name, value);
        compositeTypeBuilder.addType(name, openType);
    }

    public CompositeData build() {
        try {
            CompositeType compositeType = compositeTypeBuilder.build();
            return new CompositeDataSupport(compositeType, attributes);
        } catch (Exception e) {
            throw new RuntimeException(e); // TODO: Find something better throw.
        }
    }
}
