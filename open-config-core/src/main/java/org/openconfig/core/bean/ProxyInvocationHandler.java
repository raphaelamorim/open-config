package org.openconfig.core.bean;

import org.openconfig.core.Accessor;
import org.openconfig.core.MutableConfiguratorable;

import java.lang.reflect.Method;

/**
 * This method defines the contract for handling the invocation of methods on
 * the proxied interface. Subclasses of this interface may delegate the request
 * to the backing Configurator interface.
 *
 * @author Richard L. Burton III
 */
public interface ProxyInvocationHandler extends MutableConfiguratorable {

    /**
     * This method is invoked to handle the invocation of a method on the proxied
     * interface provided by the developer.
     *
     * @param method    The method being invoked.
     * @param name      The normalized method name.
     * @param arguments The arguments passed to the method.
     * @param access    The type of method invoked.
     * @return The return value from the invoked method.
     */
    Object handle(Method method, String name, Object[] arguments, Accessor access);

}
