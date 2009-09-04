package org.openconfig.providers;

import org.openconfig.event.ChangeStateEvent;
import org.openconfig.core.OpenConfigContext;
import static org.openconfig.util.Assert.*;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Richard L. Burton III
 * @author Dushyanth (Dee) Inguva
 */
public class CompositeDataProvider implements DataProvider {

    private Logger LOGGER = Logger.getLogger(CompositeDataProvider.class);

    private ConcurrentMap<String, DataProvider> providers = new ConcurrentHashMap<String, DataProvider>();

    public static final int MAX_NUMBER_OF_ELEMENTS = 2;

    public static final int CONFIGURATOR_INDEX = 0;

    public static final int PROPERTY_PATH_INDEX = 1;

    public Object getValue(String property) {
        String[] key = property.split("\\.", MAX_NUMBER_OF_ELEMENTS);
        String configurator = key[CONFIGURATOR_INDEX];
        String path = key[PROPERTY_PATH_INDEX];
        LOGGER.debug("Locating DataProvider for " + configurator + " and property " + property);
        DataProvider provider = providers.get(configurator);
        notNull(provider, "Unable to find provider for %s Configurator interface!", configurator);
        if(provider == null)
            throw new RuntimeException("");
        
        return provider.getValue(path);

    }

    public void onEvent(ChangeStateEvent event) {
        for (DataProvider dataProvider : providers.values()) {
            dataProvider.onEvent(event);
        }
    }

    public boolean requiresReloading() {
        for (DataProvider dataProvider : providers.values()) {
            if (dataProvider.requiresReloading()) {
                return true;
            }
        }
        return false;
    }

    public void reload() {
        for (DataProvider dataProvider : providers.values()) {
            dataProvider.reload();
        }
    }

    public void initialize(OpenConfigContext context) {
        for (DataProvider dataProvider : providers.values()) {
            dataProvider.initialize(context);
        }
    }

    public void addDataProvider(String interfaceClass, final DataProvider dataProvider) {
        providers.putIfAbsent(interfaceClass    , dataProvider);
    }

    public boolean missingDataProvider(String interfaceClass) {
        return !providers.containsKey(interfaceClass);
    }

}
