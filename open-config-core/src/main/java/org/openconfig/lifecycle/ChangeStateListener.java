package org.openconfig.lifecycle;

/**
 * This custom listener handles the change events that are emits from the
 * OpenConfig Administrator server.
 *
 * @author Richard L. Burton III
 */
public interface ChangeStateListener extends EventListener<ChangeStateEvent> {

    /**
     * This method is called when a ChangeStateEvent is emitted.
     *
     * @param changeStateEvent The change state event that contains the state in which was changed.
     */
    void onEvent(ChangeStateEvent changeStateEvent);

}
