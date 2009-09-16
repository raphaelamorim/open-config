package org.openconfig.providers;

import org.openconfig.core.InvocationContext;
import org.openconfig.core.Invocation;

import java.util.List;
import java.util.Iterator;

/**
 * @author Richard L. Burton III
 */
public abstract class AbstractReloadableDataProvider extends AbstractDataProvider {

    /**
     * Overrides AbstractDataProvider#getValue(String) to add logic that delegates
     * the checking and reloading of the data backing the DataProvider.
     *
     * @param invocationContext The property to load.
     * @return The value for the property.
     */
    public Object getValue(InvocationContext invocationContext) {
        boolean reload = requiresReloading();
        if (reload) {
            reload();
        }
        return super.getValue(getInvocationPath(invocationContext));
    }

    protected String getInvocationPath(InvocationContext invocationContext) {
        StringBuilder builder = new StringBuilder();
        Iterator<Invocation> invocations = invocationContext.getIterator();
        builder.append(invocations.next().getProperty());
        while (invocations.hasNext()) {
            Invocation invocation = invocations.next();
            builder.append('.').append(invocation.getProperty());
        }
        return builder.toString();
    }

    public abstract boolean requiresReloading();

    public abstract void reload();
}
