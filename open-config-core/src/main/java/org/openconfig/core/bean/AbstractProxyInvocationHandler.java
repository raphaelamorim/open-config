package org.openconfig.core.bean;

import org.openconfig.MutableConfigurator;

/**
 * An abstract version of the ProxyInvocationHandler that stores the Configurator reference and
 * implementation of the MutableConfigurator interface.
 *
 * @author Richard L. Burton III
 */
public abstract class AbstractProxyInvocationHandler implements ProxyInvocationHandler {

    /** The backing Configurator. */
    protected MutableConfigurator mutableConfigurator;

    public void setMutableConfigurator(MutableConfigurator mutableConfigurator) {
        this.mutableConfigurator = mutableConfigurator;
    }

    public MutableConfigurator getMutableConfigurator() {
        return mutableConfigurator;
    }

    public Object getValue(String key) {
        return mutableConfigurator.getValue(key);
    }

    public void setValue(String key, Object value) {
        mutableConfigurator.setValue(key, value);
    }
}
