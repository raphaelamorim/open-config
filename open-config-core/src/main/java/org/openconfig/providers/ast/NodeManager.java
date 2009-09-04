package org.openconfig.providers.ast;

import org.openconfig.providers.NodeVisitorContext;
import org.openconfig.providers.ast.search.ASTSearchVisitor;
import org.openconfig.providers.ast.search.ASTSearchVisitorContext;

import static java.util.Arrays.asList;
import java.util.Stack;

/**
 * A NodeManager allows you to manage a set of nodes.
 *
 * @author Richard L. Burton III
 */
public class NodeManager {

    public static final String PATH_DELIMITOR = "\\.";

    /**
     * This method finds a value for a given property path within a set of nodes.
     *
     * @param property The path to locate the node for.
     * @param tree     The set of nodes to search.
     * @return The node that matches the path.
     */
    public Node find(String property, ComplexNode tree) {
        return find(property, tree, false);
    }
    
    public Node find(String property, ComplexNode tree, boolean create) {
        String[] path = reverse(property.split(PATH_DELIMITOR));
        Stack<String> stack = new Stack<String>();
        stack.addAll(asList(path));
        return search(property, stack, tree, create);
    }

    /**
     * This method allows you to set a value for a given path.
     *
     * @param root    The nodes to search.
     * @param property The path of the node.
     * @param value    the value of the node.
     */
    @SuppressWarnings("unchecked")
    public void setValue(ComplexNode root, String property, Object value) {
        setValue(root, property, value, false);
    }

    public void setValue(ComplexNode root, String property, Object value, boolean create) {
        Node node = find(property, root, create);
        if (node != null) {
            node.setValue(value);
        } else {
            throw new UnsupportedOperationException("This operation is not yet supported for null nodes.");
        }
    }

    /**
     * Searches for node that matches the stack.
     *
     * @param property Used for debugging messages
     * @param stack    Used to locate the node
     * @param rootNode the root node to searc from
     * @param create   if true, the node is created if it is not found
     * @return the located node
     * @throws IllegalArgumentException if 
     */
    protected Node search(String property, Stack<String> stack, Node rootNode, boolean create) {
        ASTSearchVisitor searchVisitor = new ASTSearchVisitor();

        ASTSearchVisitorContext nodeVisitorContext = new ASTSearchVisitorContext(property, stack, create);
        // TODO why do I need a cast here?
        Node searchedNode = (Node)rootNode.accept(searchVisitor, nodeVisitorContext);
        return searchedNode;
    }

    private String[] reverse(String[] elements) {
        String[] reverseOrder = new String[elements.length];
        int index = 0;
        for (int i = elements.length - 1; i >= 0; i--) {
            reverseOrder[index++] = elements[i];
        }
        return reverseOrder;
    }

}
