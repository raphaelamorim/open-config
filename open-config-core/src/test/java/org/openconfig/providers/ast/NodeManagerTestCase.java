package org.openconfig.providers.ast;

import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Richard L. Burton III
 */
public class NodeManagerTestCase extends TestCase {

    private NodeTransformer transformer = new NodeTransformer();

    private NodeManager nodeManager = new NodeManager();

    public void testFindNode() {
        ComplexNode complexNode = transformer.transform(new Person());
        Set<Node> nodes = toNodeSet(complexNode);
        SimpleNode node = (SimpleNode) nodeManager.find("Person.child.name", nodes);
        assertNotNull(node);
        assertEquals("Dushyanth Inguva", node.getValue());
    }

    public void testSetValue() {
        ComplexNode complexNode = transformer.transform(new Person());
        Set<Node> nodes = toNodeSet(complexNode);
        nodeManager.setValue(nodes, "Person.name", "James");
        SimpleNode child = (SimpleNode) getChild(complexNode.getChildren(), "name");
        assertNotNull(child);
        assertEquals("James", child.getValue());
    }

    public void testSetChildValue() {
        ComplexNode complexNode = transformer.transform(new Person());
        Set<Node> nodes = toNodeSet(complexNode);
        nodeManager.setValue(nodes, "Person.child.name", "King Richard");
        ComplexNode child = (ComplexNode) getChild(complexNode.getChildren(), "child");
        assertNotNull(child);
        SimpleNode simpleChild = (SimpleNode) getChild(child.getChildren(), "name");
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

    protected Set<Node> toNodeSet(ComplexNode complexNode) {
        Set<Node> nodes = new HashSet<Node>();
        nodes.add(complexNode);
        return nodes;
    }

}
