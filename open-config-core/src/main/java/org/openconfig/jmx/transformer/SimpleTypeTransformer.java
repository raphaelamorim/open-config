package org.openconfig.jmx.transformer;

import org.openconfig.providers.ast.SimpleNode;

import javax.management.openmbean.CompositeData;
import java.util.Set;

import static org.openconfig.util.Assert.isTrue;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class SimpleTypeTransformer {

    public SimpleNode transformer(String name, CompositeData data) {
        SimpleNode node = new SimpleNode(name);
        Set<String> keys = data.getCompositeType().keySet();
        isTrue(keys.size() == 1, "Expected only one element in CompositeType#getSet() but got {0}.", keys.size());
        for (String key : keys) {
            node.setValue(data.get(key));
        }
        return node;
    }
    
}
