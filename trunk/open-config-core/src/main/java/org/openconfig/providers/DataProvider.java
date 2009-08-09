package org.openconfig.providers;

import org.openconfig.lifecyce.ChangeStateListener;
import org.openconfig.lifecyce.EventListener;
import org.openconfig.lifecyce.ChangeStateEvent;

/**
 * @author Richard L. Burton III
 */
public interface DataProvider extends EventListener<ChangeStateEvent> {

    void addChangeStateListeners(ChangeStateListener... changeListener);
    
}
