package org.openconfig.providers.ast;

import org.openconfig.providers.NodeVisitorContext;

/**
 * @author Richard L. Burton III
 */
public class SimpleNode extends AbstractNode<Object> {

    private Object value;

    public SimpleNode(String name, Object value) {
        this(name);
        this.value = value;
    }

    public SimpleNode(String name) {
        super(name);
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object accept(NodeVisitor visitor) {
        return null;
    }

    @Override
    public String toString() {
        return getName() + "='" + value + "'";
    }

    public  <J> J accept(NodeVisitor<J, NodeVisitorContext> visitor, NodeVisitorContext nodeVisitorContext) {
        return visitor.visitSimpleNode(nodeVisitorContext, this);
    }
}
