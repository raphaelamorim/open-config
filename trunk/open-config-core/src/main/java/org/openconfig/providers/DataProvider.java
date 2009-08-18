package org.openconfig.providers;

import org.openconfig.event.EventListener;
import org.openconfig.event.ChangeStateEvent;
import org.openconfig.event.ChangeStateListener;
import org.openconfig.core.Initializable;

/**
 * @author Richard L. Burton III
 */
public interface DataProvider extends EventListener<ChangeStateEvent>, Initializable {

    Object getValue(String name);


    boolean requiresReloading();

    void reload();

}
