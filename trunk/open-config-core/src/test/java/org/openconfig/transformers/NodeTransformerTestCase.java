package org.openconfig.transformers;

import junit.framework.TestCase;
import org.openconfig.transformers.BeanToNodeTransformer;
import org.openconfig.providers.ast.AbstractNode;
import org.openconfig.providers.ast.Person;

/**
 * @author Richard L. Burton III
 */
public class NodeTransformerTestCase extends TestCase {

    public void testBasic() throws Exception {
        BeanToNodeTransformer transformer = new BeanToNodeTransformer();
        AbstractNode node = transformer.transform(new Person());
        assertEquals("Person", node.getName());
    }
}

