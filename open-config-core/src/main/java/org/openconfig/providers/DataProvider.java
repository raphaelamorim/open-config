package org.openconfig.providers;

import org.openconfig.lifecycle.EventListener;
import org.openconfig.lifecycle.ChangeStateEvent;
import org.openconfig.lifecycle.ChangeStateListener;

/**
 * @author Richard L. Burton III
 */
public interface DataProvider extends EventListener<ChangeStateEvent> {

    void addChangeStateListeners(ChangeStateListener... changeListener);

    Object getValue(String name);
    
}
