package org.openconfig.jmx.transformer;

import org.openconfig.providers.ast.ComplexNode;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeType;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class OpenMBeanTranformer {

    private CompositeDataTransformer compositeDataTransformer;


    public OpenMBeanTranformer() {
        compositeDataTransformer = new CompositeDataTransformer();
    }

    public Set<ComplexNode> transform(CompositeData compositeData) {
        CompositeType compositeType = compositeData.getCompositeType();
        Set<String> keys = compositeType.keySet();
        Set<ComplexNode> complexNodes = new HashSet<ComplexNode>();
        for (String key : keys) {
            CompositeData configuration = (CompositeData) compositeData.get(key);
            ComplexNode complexNode = compositeDataTransformer.transform(key, configuration);
            complexNodes.add(complexNode);
        }
        return complexNodes;
    }

}
