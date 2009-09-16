package org.openconfig.core;

import com.google.inject.Inject;
import org.openconfig.Configurator;
import org.openconfig.providers.DataProvider;

/**
 * Adapts a DataProvider to a Configurator.
 *
 * @author Dushyanth (Dee) Inguva
 */
public class DataProviderToConfiguratorAdapter implements Configurator {

    private DataProvider dataProvider;

    public DataProviderToConfiguratorAdapter() {

    }

    public DataProviderToConfiguratorAdapter(DataProvider dataProvider) {
        this();
        this.dataProvider = dataProvider;
    }

    public Object getValue(String key) {
        InvocationContext invocationContext = new InvocationContext(Configurator.class);
        invocationContext.addInvocation(new Invocation(null, key)); // TODO: Fix the Null for Method
        return dataProvider.getValue(invocationContext);
    }
}
