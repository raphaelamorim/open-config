package org.openconfig.event;

import java.util.List;
import java.util.LinkedList;
import static java.util.Arrays.asList;

/**
 * Publishes the events to all the registered listeners.
 *
 * @author Dushyanth (Dee) Inguva
 * @author Richard L. Burton III
 */
public class DefaultEventPublisher implements EventPublisher {

    private final List<EventListener> eventListeners = new LinkedList<EventListener>();

    // TODO Temporary. Will be refactored after we define a proper event hierarchy
    public void publishEvent(ChangeStateEvent event) {
        for (EventListener listener : eventListeners) {
            listener.onEvent(event);
        }
    }

    public void addListeners(EventListener... listeners) {
        eventListeners.addAll(asList(listeners));
    }

}
