package org.openconfig.jmx.transformer;

import org.openconfig.providers.ast.ComplexNode;
import org.openconfig.providers.ast.SimpleNode;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import java.util.Set;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class CompositeDataTransformer {

    private SimpleTypeTransformer simpleTypeTransformer;

    public ComplexNode transform(String name, CompositeData source) {
        ComplexNode complexNode = new ComplexNode(name);

        CompositeType compositeType = source.getCompositeType();
        Set<String> keys = compositeType.keySet();

        for (String key : keys) {
            OpenType type = compositeType.getType(key);
            if (type instanceof CompositeType) {
                CompositeData attributes = (CompositeData) source.get(key);
                ComplexNode cn = transform(key, attributes);
                complexNode.addChild(cn);
            } else if (type instanceof SimpleType) {
                SimpleNode node = new SimpleNode(key, source.get(key));
                complexNode.addChild(node);
            } else {
                throw new IllegalArgumentException("Unsupported OpenType.. " + type.getTypeName());
            }
        }

        return complexNode;
    }

    public void setSimpleTypeTransformer(SimpleTypeTransformer simpleTypeTransformer) {
        this.simpleTypeTransformer = simpleTypeTransformer;
    }

}
