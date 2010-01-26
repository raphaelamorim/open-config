package org.openconfig.providers.ast;

import org.openconfig.providers.NodeVisitorContext;

import java.util.Map;
import java.util.Collection;
import java.util.HashMap;

/**
 * This class models a JavaBean.
 *
 * @author Richard L. Burton III
 */
public class ComplexNode extends AbstractNode<Object> {

    private Map<String, AbstractNode> children = new HashMap<String, AbstractNode>();

    public static final String ROOT_NAME = "root";

    public ComplexNode() {
        super(ROOT_NAME);
    }

    public ComplexNode(String name) {
        this(name, new HashMap<String, AbstractNode>());
    }

    public ComplexNode(String name, Map<String, AbstractNode> children) {
        super(name);
        this.children = children;
    }

    /**
     *
     * @param name
     * @return the child with the name, or null if none
     */
    public AbstractNode getChild(String name) {
        return children.get(name);
    }

    public Collection<AbstractNode> getChildren() {
        return children.values();
    }

    public void addChild(AbstractNode node) {
        children.put(node.getName(), node);
    }

    public void setValue(Object child) {
        throw new UnsupportedOperationException("Not coded yet");
    }

    public <J> J accept(NodeVisitor<J, NodeVisitorContext> visitor, NodeVisitorContext nodeVisitorContext) {
        return visitor.visitComplexNode(nodeVisitorContext, this);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("ComplexNode(name: \'")
                .append(getName()).append("\')\n\t");
        builder.append("attributes: (\n\t");
        builder.append(children);
        builder.append("\t)");
        return builder.toString();
    }
}
