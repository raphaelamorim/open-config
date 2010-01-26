package org.openconfig.providers;

import org.openconfig.event.EventListener;
import org.openconfig.event.ChangeStateEvent;
import org.openconfig.event.ChangeStateListener;
import org.openconfig.core.Initializable;
import org.openconfig.core.InvocationContext;

/**
 * This class defines a DataProvider which is a means of representing the client side configuration
 * information. Implementations of this interface are used to back the Configurators with the data
 * used to answer the method invocations. For more information, see the already existing DataProviders
 * such as XmlDataProvider.
 * @author Richard L. Burton III, SmartCode LLC
 */
public interface DataProvider extends Initializable {

    /**
     * This method is used to obtain the value for an InvocationContext which will
     * reflect a method invocation.
     * @param invocationContext The invocation that triggered the query.
     * @return The value which is located by the InvocationContext.
     */
    Object getValue(InvocationContext invocationContext);

    /**
     * A method which defines whether the DataProvider should have it's reload method invoked. This
     * encourages subclasses to keep a separation between the actual reloading vs. knowing when it
     * should reload.
     * @return true indicates the DataProvider should reload, false otherwise.
     */
    boolean requiresReloading();

    /**
     * This method is used to notify a DataProvider that it should reload its internal
     * Abstract Syntax Tree (AST) with a fresh copy of the data backing it.
     */
    void reload();

    /**
     * Registers the event listeners for the given configurator.
     * @param configurator the configurator whose events will be fired
     * @param eventListeners the event listeners which will be notified of the events
     */
    void registerEventListeners(String configurator, EventListener... eventListeners);

}
