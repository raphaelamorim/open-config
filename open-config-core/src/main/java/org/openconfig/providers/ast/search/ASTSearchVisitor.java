package org.openconfig.providers.ast.search;

import org.openconfig.providers.ast.ComplexNode;
import org.openconfig.providers.ast.AbstractNode;
import org.openconfig.providers.ast.NodeVisitor;
import org.openconfig.providers.ast.SimpleNode;
import org.openconfig.util.Assert;

/**
 * Helps in searching nodes based on a path.
 *
 * @author Dushyanth (Dee) Inguva
 */
public class ASTSearchVisitor implements NodeVisitor<AbstractNode, ASTSearchVisitorContext> {


    /**
     * Performs simple assertions and returns the node.
     *
     * @param node
     * @return
     */
    public AbstractNode visitSimpleNode(ASTSearchVisitorContext context, SimpleNode node) {
        Assert.isTrue(context.size() == 0, "Unconsumed context node %s %s", node, context);
        Assert.notNull(node, "Cannot locate node for context: %s", context);
        return node;
    }

    /**
     * Recursively searches for the node and returns it.
     *
     * @param context The context which is used in searching.
     * @param node    the current complex node
     * @return
     */
    public AbstractNode visitComplexNode(ASTSearchVisitorContext context, ComplexNode node) {

        if (context.isEmpty()) {
            return node;
        }
        String childName = context.pop();
        AbstractNode child = node.getChild(childName);
        if (child == null) {

            if (context.create) {
                if (context.isEmpty()) {
                    child = new SimpleNode(childName);
                } else {
                    child = new ComplexNode(childName);
                }
                node.addChild(child);
            } else {
                throw new IllegalStateException("Cannot find the property: " + childName + " in path " + context.qualifiedName);
            }

        }
        // TODO why do I need to cast here?
        return (AbstractNode) child.accept(this, context);
    }
}
