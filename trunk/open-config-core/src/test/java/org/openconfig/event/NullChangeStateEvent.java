package org.openconfig.event;

import org.openconfig.providers.ast.Node;

import java.util.Set;

/**
 * @author Richard L. Burton III
 */
public class NullChangeStateEvent implements ChangeStateEvent{
    
    public Set<String> getChangedPaths() {
        return null;
    }

    public Set<Node> getChangeState() {
        return null;
    }

    public Set<Node> getState() {
        return null;
    }

    public Node find(String property) {
        return null;
    }
}
