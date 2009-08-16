package org.openconfig.providers;

import org.openconfig.event.EventListener;
import org.openconfig.event.ChangeStateEvent;
import org.openconfig.event.ChangeStateListener;

/**
 * @author Richard L. Burton III
 */
public interface DataProvider extends EventListener<ChangeStateEvent> {

    void addChangeStateListeners(ChangeStateListener... changeListener);

    Object getValue(String name);
    
}
