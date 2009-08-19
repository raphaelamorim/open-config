package org.openconfig.providers.ast.search;

import org.openconfig.providers.NodeVisitorContext;

import java.util.Stack;

/**
 * Context for ASTSearchVisitor.
 *
 * @author Dushyanth (Dee) Inguva
 */
public class ASTSearchVisitorContext implements NodeVisitorContext {

    private final Stack<String> path;
    public final String qualifiedName;
    public final boolean create;

    /**
     *
     * @param qualifiedName
     * @param path
     * @param create if true, the nodes are created if they are not found
     */
    public ASTSearchVisitorContext(String qualifiedName, Stack<String> path, boolean create) {
        this.qualifiedName = qualifiedName;
        this.create = create;
        // TODO Optimize to create a zero copy
        this.path = new Stack();
        this.path.addAll(path);
    }

    public int size() {
        return path.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public String pop() {
        return path.pop();
    }

    @Override
    public String toString() {
        return "NodeVisitorContext{" +
                "qualifiedName='" + qualifiedName + '\'' +
                '}';
    }
}
