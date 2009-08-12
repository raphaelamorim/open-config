package org.openconfig.providers.ast;

import java.util.Set;

/**
 * This class models a JavaBean.
 *
 * @author Richard L. Burton III
 */
public class ComplexNode extends Node<Object> {

    private Set<Node> children;

    public ComplexNode(String name, Set<Node> children) {
        super(name);
        this.children = children;
    }

    public Set<Node> getChildren() {
        return children;
    }

    public void addChild(Node node) {
        children.add(node);
    }

    public void setValue(Object child) {
        if (child instanceof Node) {
            if (children.contains(child)) {
                for(Node node : children){
                    node.setValue(child);
                }
            }
        }else{
            throw new UnsupportedOperationException("Unsupported operation for appending dynamically created SimpleNode objects.");
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("ComplexNode(name: \'")
                .append(getName()).append("\')\n\t");
        builder.append("attributes: (\n\t");
        for (Node node : children) {
            builder.append(node).append("\n\t");
        }
        builder.append("\t)");
        return builder.toString();
    }
}
