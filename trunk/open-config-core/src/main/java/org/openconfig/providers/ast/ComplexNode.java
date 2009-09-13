package org.openconfig.providers.ast;

import org.openconfig.providers.NodeVisitorContext;

import java.util.Set;
import java.util.Map;
import java.util.Collection;
import java.util.HashMap;

/**
 * This class models a JavaBean.
 *
 * @author Richard L. Burton III
 */
public class ComplexNode extends Node<Object> {

    private Map<String, Node> children = new HashMap<String,Node>();

    public static final String ROOT_NAME = "root";

    public ComplexNode() {
        super(ROOT_NAME);
    }

    public ComplexNode(String name) {
        this(name, new HashMap<String, Node>());
    }

    public ComplexNode(String name, Map<String, Node> children) {
        super(name);
        this.children = children;
    }

    /**
     *
     *
     * @param name
     * @return the child with the name, or null if none
     */
    public Node getChild(String name) {
        return children.get(name);
    }

    public Collection<Node> getChildren() {
        return children.values();
    }

    public void addChild(Node node) {
        children.put(node.getName(), node);
    }

    public void setValue(Object child) {
        throw new UnsupportedOperationException("Not coded yet");
//        if (child instanceof Node) {
//            if (children.containsKey(((Node)child).getName())) {
//                for(Node node : children){
//                    node.setValue(child);
//                }
//            }
//        }else{
//            throw new UnsupportedOperationException("Unsupported operation for appending dynamically created SimpleNode objects.");
//        }
    }

    public <J> J accept(NodeVisitor<J, NodeVisitorContext> visitor, NodeVisitorContext nodeVisitorContext) {
        return visitor.visitComplexNode(nodeVisitorContext, this);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("ComplexNode(name: \'")
                .append(getName()).append("\')\n\t");
        builder.append("attributes: (\n\t");
//        for (Node node : children) {
//            builder.append(node).append("\n\t");
//        }
        builder.append("\t)");
        return builder.toString();
    }
}
