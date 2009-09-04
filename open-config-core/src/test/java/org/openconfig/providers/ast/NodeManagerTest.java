package org.openconfig.providers.ast;

import junit.framework.TestCase;

import java.util.Set;

import org.openconfig.transformers.BeanToNodeTransformer;

/**
 * @author Richard L. Burton III
 */
public class NodeManagerTest extends TestCase {

    private BeanToNodeTransformer transformer = new BeanToNodeTransformer();

    private NodeManager nodeManager = new NodeManager();

    public void testFindNode() {
        ComplexNode complexNode = transformer.transform(new Person());
        SimpleNode node = (SimpleNode) nodeManager.find("child.name", complexNode);
        assertNotNull(node);
    }

    public void testSetValue() {
        ComplexNode complexNode = transformer.transform(new Person());
        nodeManager.setValue(complexNode, "name", "James");
        SimpleNode child = (SimpleNode) complexNode.getChild("name");
        assertNotNull(child);
        assertEquals("James", child.getValue());
    }

    public void testSetChildValue() {
        ComplexNode complexNode = transformer.transform(new Person());
        nodeManager.setValue(complexNode, "child.name", "King Richard");
        ComplexNode child = (ComplexNode) complexNode.getChild("child");
        assertNotNull(child);
        SimpleNode simpleChild = (SimpleNode) child.getChild("name");
        assertEquals("King Richard", simpleChild.getValue());
    }

    protected Node getChild(Set<Node> children, String name) {
        for (Node node : children) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }

}
