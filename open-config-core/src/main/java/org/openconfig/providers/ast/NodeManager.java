package org.openconfig.providers.ast;

import java.util.Set;
import java.util.Stack;
import static java.util.Arrays.asList;

/**
 * A NodeManager allows you to manage a set of nodes.
 * @author Richard L. Burton III
 */
public class NodeManager {

    public static String PATH_DELIMITOR = "\\.";

    /**
     * This method finds a value for a given property path within a set of nodes.
     * @param property The path to locate the node for.
     * @param tree The set of nodes to search.
     * @return The node that matches the path.
     */
    public Node find(String property, Set<Node> tree) {
        String[] path = reverse(property.split(PATH_DELIMITOR));
        Stack<String> stack = new Stack<String>();
        stack.addAll(asList(path));
        return search(stack, tree);
    }

    /**
     * This method allows you to set a value for a given path.
     * @param nodes The nodes to search.
     * @param property The path of the node.
     * @param value the value of the node.
     */
    @SuppressWarnings("unchecked")
    public void setValue(Set<Node> nodes, String property, Object value){
        Node node = find(property, nodes);
        if(node != null){
            node.setValue(value);
        }else{
            throw new UnsupportedOperationException("This operation is not yet supported for null nodes.");
        }
    }

    protected Node search(Stack<String> stack, Set<Node> ast) {
        for (Node node : ast) {
            String name = stack.peek();
            String nodeName = node.getName();
            if (!stack.isEmpty() && nodeName.equals(name)) {
                stack.pop();
                if (node instanceof ComplexNode) {
                    if (!stack.isEmpty()) {
                        return search(stack, ((ComplexNode) node).getChildren());
                    } else {
                        return node;
                    }
                } else if (node instanceof SimpleNode) {
                    return node;
                }
            }
        }
        return null;
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
