package org.openconfig.providers.ast;

import junit.framework.TestCase;

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

