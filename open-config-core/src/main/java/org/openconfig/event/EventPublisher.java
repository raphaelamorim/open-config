package org.openconfig.event;

/**
 * Interface that encapsulates event publication functionality. 
 *
 * @author Dushyanth (Dee) Inguva
 */
public interface EventPublisher {

     void publishEvent(ChangeStateEvent event);
}
