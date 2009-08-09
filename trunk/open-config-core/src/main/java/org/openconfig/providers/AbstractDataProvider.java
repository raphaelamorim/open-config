package org.openconfig.providers;

import org.openconfig.providers.ast.Node;
import org.openconfig.lifecyce.ChangeStateListener;
import org.openconfig.lifecyce.ChangeStateEvent;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
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
        for(ChangeStateListener listener : listeners){
            listener.onEvent(event);
        }
    }

}
