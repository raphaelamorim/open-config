package org.openconfig.event;

import org.openconfig.providers.ast.AbstractNode;
import org.openconfig.providers.ast.ComplexNode;

import java.util.Set;

/**
 * Defines a ChangeStateEvent that is triggered when any state on the remote server is changed.
 *
 * @author Richard L. Burton III
 */
public interface ChangeStateEvent {

    /** A set of strings that represent the full path of the method invocation
     * that triggered the change in the node. e.g., person.child.name
     * @return A set of node paths that were changed.
     */
    Set<String> getChangedPaths();
    
    /**
     * Returns a Set of the properties that were changed.
     * @return The names of the properties that were changed.
     */
    ComplexNode getChangeState();

    ComplexNode getState();

    AbstractNode find(String property);

}
