package org.openconfig.providers.ast;

import org.openconfig.providers.NodeVisitorContext;

import java.util.Map;
import java.util.Collection;
import java.util.HashMap;

/**
 * A ComplexNode is an internal representation of an object in a very loose structure. To see
 * how an object is represented using a ComplexNode, the below class is used as an example.
 *
 * <pre>public class Person {
 *      private String name;
 *      private int age;
 * }
 *
 * new Person("richard", 30);
 * </pre>
 *
 * The above class and creation would be represented using the following internal AST.
 *
 * <pre>ComplexNode person = new ComplexNode("person");
 *
 * SimpleNode nameAttribute = new SimpleNode("name", "richard");
 * person.addChild(nameAttribute);
 *
 * SimpleNode ageAttribute = new SimpleNode("name", 30);
 * person.addChild(ageAttribute);
 * </pre>
 * 
 * @author Richard L. Burton III, SmartCode LLC
 */
public class ComplexNode extends AbstractNode<Object> {

    /** The Attributes which belong to the ComplexNode. */
    private Map<String, AbstractNode> children = new HashMap<String, AbstractNode>();

    /** The name of all root nodes. */
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
