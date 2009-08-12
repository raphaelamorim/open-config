package org.openconfig.providers;

import org.openconfig.providers.ast.Node;
import org.openconfig.lifecycle.ChangeStateListener;
import org.openconfig.lifecycle.ChangeStateEvent;

import java.util.*;
import static java.util.Arrays.asList;

/**
 * @author Richard L. Burton III
 */
public class AbstractDataProvider implements DataProvider {

    private Map<String, Node> cache = new HashMap<String, Node>();

    private List<ChangeStateListener> listeners = new LinkedList<ChangeStateListener>();

    public void addNode(Node node){
        cache.put(node.getName(), node);
    }

    public void removeNode(Node node){
        cache.remove(node.getName());
    }

    public void addChangeStateListeners(ChangeStateListener... changeListeners) {
        listeners.addAll(asList(changeListeners));
    }

    public void onEvent(ChangeStateEvent event) {
        Set<Node> changedNodes = event.getChangeState();
        for(Node node : changedNodes){
            cache.containsKey(node.getName());
        }
        for(ChangeStateListener listener : listeners){
            listener.onEvent(event);
        }
    }

}
