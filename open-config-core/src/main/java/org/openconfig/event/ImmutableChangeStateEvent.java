package org.openconfig.event;

import org.openconfig.providers.ast.*;

import java.util.Set;
import static java.util.Collections.*;

/**
 * Defines an immutable implementation of the ChangeStateEvent.
 *
 * @author Richard L. Burton III
 */
public class ImmutableChangeStateEvent implements ChangeStateEvent {

    private ComplexNode changeState;

    private ComplexNode root;

    private Set<String> changedPaths;

    private NodeManager nodeFinder = new NodeManager();

    public ImmutableChangeStateEvent(ComplexNode changeState, ComplexNode root, Set<String> paths) {
        this.changeState = changeState;
        this.changedPaths = unmodifiableSet(paths);
        this.root = root;
    }

    public ImmutableChangeStateEvent(ComplexNode changeState) {
        this.changeState = changeState;
        this.changedPaths = EMPTY_SET;
    }

    public ComplexNode getChangeState() {
        return changeState;
    }

    public Set<String> getChangedPaths() {
        return changedPaths;
    }

    public ComplexNode getState(){
        return root;
    }

    /**
     * @complex
     */
    public Node find(String property) {
        return nodeFinder.find(property, changeState);
    }

}
