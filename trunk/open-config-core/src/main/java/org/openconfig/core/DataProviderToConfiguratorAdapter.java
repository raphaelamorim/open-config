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

    @Inject
    private DataProvider dataProvider;

    private final String configuratorName = Configurator.class.getSimpleName();

    public DataProviderToConfiguratorAdapter() {

    }

    public DataProviderToConfiguratorAdapter(DataProvider dataProvider) {
        this();
        this.dataProvider = dataProvider;
    }

    public Object getValue(String key) {
        return dataProvider.getValue(configuratorName + "." + key);
    }
}
