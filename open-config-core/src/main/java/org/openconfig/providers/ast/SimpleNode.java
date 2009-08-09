package org.openconfig.providers.ast;

/**
 * @author Richard L. Burton III
 */
public class SimpleNode extends Node {

    private Object value;

    public SimpleNode(String name, Object value) {
        super(name);
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SimpleNode{" + super.toString() + ", " +
                "value=" + value +
                '}';
    }
}
