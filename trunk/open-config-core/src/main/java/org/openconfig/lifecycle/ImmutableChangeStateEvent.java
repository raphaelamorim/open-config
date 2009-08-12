package org.openconfig.lifecycle;

import org.openconfig.providers.ast.*;

import java.util.Set;
import static java.util.Collections.*;

/**
 * Defines an immutable implementation of the ChangeStateEvent.
 *
 * @author Richard L. Burton III
 */
public class ImmutableChangeStateEvent implements ChangeStateEvent {

    private Set<Node> changeState;

    private Set<String> changedPaths;

    private NodeManager nodeFinder = new NodeManager();

    public ImmutableChangeStateEvent(Set<Node> changeState, Set<String> paths) {
        this.changeState = unmodifiableSet(changeState);
        this.changedPaths = unmodifiableSet(paths);
    }

    public ImmutableChangeStateEvent(Set<Node> changeState) {
        this.changeState = unmodifiableSet(changeState);
        this.changedPaths = EMPTY_SET;
    }

    public Set<Node> getChangeState() {
        return changeState;
    }

    public Set<String> getChangedPaths() {
        return changedPaths;
    }

    /**
     * @complex
     */
    public Node find(String property) {
        return nodeFinder.find(property, changeState);
    }

}