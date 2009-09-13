package org.openconfig.providers;

import org.openconfig.providers.ast.NodeManager;
import org.openconfig.providers.ast.ComplexNode;
import org.openconfig.providers.ast.SimpleNode;
import org.openconfig.event.ChangeStateEvent;
import org.openconfig.event.EventPublisher;

/**
 * @author Richard L. Burton III
 */
public abstract class AbstractDataProvider implements DataProvider {

    private ComplexNode root = new ComplexNode("root");

    protected NodeManager nodeFinder = new NodeManager();

    protected EventPublisher eventPublisher;

    public Object getValue(String name) {
        return ((SimpleNode)nodeFinder.find(name, root)).getValue();
    }

    protected void setRoot(ComplexNode root) {
        // TODO use an AtomicReference here?
        this.root = root;
    }

    /**
     * @see org.openconfig.event.EventListener
     */
    public void onEvent(ChangeStateEvent event) {
        root = event.getState();
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
