package org.openconfig.providers;

import org.openconfig.event.EventListener;
import org.openconfig.event.ChangeStateEvent;
import org.openconfig.event.ChangeStateListener;
import org.openconfig.core.Initializable;
import org.openconfig.core.InvocationContext;

/**
 * @author Richard L. Burton III
 */
public interface DataProvider extends Initializable {

    Object getValue(InvocationContext invocationContext);

    boolean requiresReloading();

    void reload();

    /**
     * Registers the event listeners for the given configurator.
     *
     * @param configurator the configurator whose events will be fired
     * @param eventListeners the event listeners which will be notified of the events
     */
    void registerEventListeners(String configurator, EventListener... eventListeners);

}
