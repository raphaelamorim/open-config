package org.openconfig.providers.ast;

import org.openconfig.providers.NodeVisitorContext;

import java.io.Serializable;

/**
 * @author Richard L. Burton III
 */
public abstract class AbstractNode<T> implements Serializable {

    protected String name;

    public AbstractNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractNode node = (AbstractNode) o;

        if (name != null ? !name.equals(node.name) : node.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'';
    }

    public abstract void setValue(T value);

    public abstract <J> J accept(NodeVisitor<J, NodeVisitorContext> visitor, NodeVisitorContext context);

}
