package org.openconfig.lifecyce;

import org.openconfig.providers.ast.Node;
import org.openconfig.providers.ast.ComplexNode;
import org.openconfig.providers.ast.SimpleNode;

import java.util.Set;
import java.util.Stack;
import static java.util.Arrays.asList;

/**
 * Defines an immutable implementation of the ChangeStateEvent.
 *
 * @author Richard L. Burton III
 */
public class ImmutableChangeStateEvent implements ChangeStateEvent {

    private Set<Node> changeState;

    public ImmutableChangeStateEvent(Set<Node> changeState) {
        this.changeState = changeState;
    }


    public Set<Node> getChangeState() {
        return changeState;
    }

    /**
     * @complex
     */
    public Node find(String property) {
        String[] path = reverse(property.split("\\."));
        Stack<String> stack = new Stack<String>();
        stack.addAll(asList(path));
        return search(stack, changeState);
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
