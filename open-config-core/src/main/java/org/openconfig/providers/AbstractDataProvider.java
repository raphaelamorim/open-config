package org.openconfig.providers;

import org.openconfig.providers.ast.Node;
import org.openconfig.providers.ast.NodeManager;
import org.openconfig.event.ChangeStateEvent;
import org.openconfig.event.EventPublisher;

import java.util.*;

/**
 * @author Richard L. Burton III
 */
public abstract class AbstractDataProvider implements DataProvider {

    private Set<Node> cache = new HashSet<Node>();

    protected NodeManager nodeFinder = new NodeManager();

    protected EventPublisher eventPublisher;

    public Object getValue(String name) {
        return nodeFinder.find(name, cache);
    }

    /**
     * @see org.openconfig.event.EventListener
     */
    public void onEvent(ChangeStateEvent event) {
        cache = event.getState();
    }

    public void setEventPublisher(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * A helper method that delegates the publishing of events to the <tt>EventPublisher</tt>.
     * @param changeStateEvent The event to publish to all listeners.
     */
    protected void publish(ChangeStateEvent changeStateEvent){
        eventPublisher.publishEvent(changeStateEvent);
    }
    
}
