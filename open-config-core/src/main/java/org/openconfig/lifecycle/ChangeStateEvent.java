package org.openconfig.lifecycle;

import org.openconfig.providers.ast.Node;

import java.util.Set;

/**
 * Defines a ChangeStateEvent that is triggered when any state on the remote server is changed.
 *
 * @author Richard L. Burton III
 */
public interface ChangeStateEvent {

    /**
     * Returns a Set of the properties that were changed.
     * @return The names of the properties that were changed.
     */
    Set<Node> getChangeState();

    Node find(String property);

}
