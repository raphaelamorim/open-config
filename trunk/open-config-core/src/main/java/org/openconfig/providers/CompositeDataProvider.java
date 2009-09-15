package org.openconfig.providers;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.apache.log4j.Logger;
import org.openconfig.core.BasicOpenConfigContext;
import org.openconfig.core.OpenConfigContext;
import org.openconfig.event.ChangeStateEvent;
import org.openconfig.ioc.config.OpenConfigConfiguration;
import static org.openconfig.util.Assert.notNull;

import java.util.HashMap;
import java.util.Map;
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

    @Inject
    private OpenConfigConfiguration openConfigConfiguration;

    @Inject
    private Injector injector;

    public Object getValue(String property) {
        String[] key = property.split("\\.", MAX_NUMBER_OF_ELEMENTS);

        ensureDataProviderExists(key);

        String configurator = key[CONFIGURATOR_INDEX];
        String path = key[PROPERTY_PATH_INDEX];
        LOGGER.debug("Locating DataProvider for " + configurator + " and property " + property);
        DataProvider provider = providers.get(configurator);
        notNull(provider, "Unable to find provider for %s Configurator interface!", configurator);

        return provider.getValue(path);

    }

    /**
     * Checks if the data provider exists. If not, creates it.
     *
     * @param key the key which contains the interface name
     */
    private void ensureDataProviderExists(String[] key) {
        String interfaceName = key[0].trim();
        if (missingDataProvider(interfaceName)) {
            addDataProvider(interfaceName, createDataProvider(interfaceName));
        }
    }

    /**
     * Creates the data provider.
     *
     * @param interfaceName the interface name which will be used to load the corresponding configuration file
     * @return an initialized data provider
     */
    private DataProvider createDataProvider(String interfaceName) {
        // TODO change openConfigConfiguration to be strongly typed
        Class<? extends DataProvider> dataProviderClass = (Class<? extends DataProvider>) openConfigConfiguration.getClass("DataProvider");
        DataProvider dataProvider = injector.getInstance(dataProviderClass);
        Map<String, String> temp = new HashMap<String, String>();
        temp.put("interface", interfaceName);
        dataProvider.initialize(new BasicOpenConfigContext(temp));
        return dataProvider;
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
        providers.putIfAbsent(interfaceClass, dataProvider);
    }

    public boolean missingDataProvider(String interfaceClass) {
        return !providers.containsKey(interfaceClass);
    }

}
