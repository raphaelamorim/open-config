package org.openconfig.providers;

import org.openconfig.providers.ast.Node;
import org.openconfig.providers.ast.NodeManager;
import org.openconfig.event.ChangeStateListener;
import org.openconfig.event.ChangeStateEvent;
import org.openconfig.event.ImmutableChangeStateEvent;

import java.util.*;
import static java.util.Arrays.asList;

/**
 * @author Richard L. Burton III
 */
public abstract class AbstractDataProvider implements DataProvider {

    private Set<Node> cache = new HashSet<Node>();

    private List<ChangeStateListener> listeners = new LinkedList<ChangeStateListener>();

    private NodeManager nodeFinder = new NodeManager();

    public void addChangeStateListeners(ChangeStateListener... changeListeners) {
        listeners.addAll(asList(changeListeners));
    }

    public Object getValue(String name) {
        return nodeFinder.find(name, cache);
    }

    public void onEvent(ChangeStateEvent event) {
        cache = event.getState();
        for (ChangeStateListener listener : listeners) {
            listener.onEvent(event);
        }
    }

    protected void updateState(Set<Node> state){
        ChangeStateEvent cse = new ImmutableChangeStateEvent(state, state, new HashSet<String>());
        onEvent(cse);
    }
    
}
