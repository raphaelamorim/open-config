package org.openconfig.providers.ast;

/**
 * Generic visitor to Node tree.
 *
 * @author Dushyanth (Dee) Inguva
 */
public interface NodeVisitor<J,K> {

    /**
     * Visits simple node.
     *
     * @param node
     * @return
     */
    public J visitSimpleNode(K context, SimpleNode node);

    /**
     * Visits complex node.
     *
     * @param node
     * @return
     */
    public J visitComplexNode(K context, ComplexNode node);
}
