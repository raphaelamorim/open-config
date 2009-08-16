package org.openconfig.event;

/**
 * Interface that encapsulates event publication functionality. 
 *
 * @author Dushyanth (Dee) Inguva
 */
public interface EventPublisher {

    /**
     * Publishes the events to the listeners registered through {@link org.openconfig.factory.ConfiguratorFactory}.
     *
     * @param event the event to publish
     */
    void publishEvent(ChangeStateEvent event);
}
