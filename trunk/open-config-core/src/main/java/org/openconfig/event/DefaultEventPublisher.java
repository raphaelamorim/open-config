package org.openconfig.event;

/**
 * Publishes the events to all the registered listeners.
 * 
 * @author Dushyanth (Dee) Inguva
 */
public class DefaultEventPublisher implements EventPublisher {

    private final EventListener[] eventListeners;

    public DefaultEventPublisher(EventListener... listeners) {
        this.eventListeners = listeners;
    }

    public void publishEvent(ChangeStateEvent event) {
        for (EventListener listener: eventListeners) {
            // TODO Temporary. Will be refactored after we define a proper event hierarchy
            listener.onEvent(event);
        }
    }
}
