package org.openconfig.lifecyce;

/**
 * Defines the contract for all event listeners.
 *
 * @author Richard L. Burton III
 */
public interface EventListener<T> {

    /**
     * This method will get triggered upon receiving of an event from
     * the remote administrator service or a changed within OpenConfig
     * on the client side.
     *
     * @param event The event that was emitted.
     */
    void onEvent(T event);
}
