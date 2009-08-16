package org.openconfig.providers.ast.transformers;

import junit.framework.TestCase;
import org.openconfig.providers.ast.transformers.BeanToNodeTransformer;
import org.openconfig.providers.ast.Node;
import org.openconfig.providers.ast.Person;

/**
 * @author Richard L. Burton III
 */
public class NodeTransformerTestCase extends TestCase {

    public void testBasic() throws Exception {
        BeanToNodeTransformer transformer = new BeanToNodeTransformer();
        Node node = transformer.transform(new Person());
        assertEquals("Person", node.getName());
    }
}

