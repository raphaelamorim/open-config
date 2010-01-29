package org.openconfig.providers;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.apache.log4j.Logger;
import org.openconfig.core.BasicOpenConfigContext;
import org.openconfig.core.OpenConfigContext;
import org.openconfig.core.InvocationContext;
import org.openconfig.event.ChangeStateEvent;
import org.openconfig.event.EventListener;
import org.openconfig.ioc.config.OpenConfigConfiguration;
import org.openconfig.util.Assert;

import static org.openconfig.util.Assert.isTrue;
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

    private Map<String, EventListener[]> configuratorToEventListenerMap = new HashMap<String, EventListener[]>();

    @Inject
    private OpenConfigConfiguration openConfigConfiguration;

    @Inject
    private Injector injector;

    public Object getValue(InvocationContext invocationContext) {
        String key = invocationContext.getConfiguratorName();

        ensureDataProviderExists(key);

        LOGGER.debug("Locating DataProvider for " + key + " and property " + invocationContext.getLastInvocation().getProperty());
        DataProvider provider = providers.get(key);
        notNull(provider, "Unable to find provider for %s Configurator interface!", invocationContext.getConfiguratorName());

        return provider.getValue(invocationContext);

    }

    /**
     * Checks if the data provider exists. If not, creates it.
     *
     * @param configuratorName the key which contains the interface name
     */
    private void ensureDataProviderExists(String configuratorName) {
        String interfaceName = configuratorName.trim();
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
        // TODO: change openConfigConfiguration to be strongly typed
        Class<? extends DataProvider> dataProviderClass = (Class<? extends DataProvider>) openConfigConfiguration.getClass("DataProvider");
        DataProvider dataProvider = injector.getInstance(dataProviderClass);
        Map<String, String> temp = new HashMap<String, String>();
        temp.put("interface", interfaceName);
        dataProvider.initialize(new BasicOpenConfigContext(temp));
        return dataProvider;
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
        if(configuratorToEventListenerMap.containsKey(interfaceClass)) {
            dataProvider.registerEventListeners(interfaceClass, configuratorToEventListenerMap.get(interfaceClass));
        }
    }

    private boolean missingDataProvider(String interfaceClass) {
        return !providers.containsKey(interfaceClass);
    }

    public void registerEventListeners(String configurator, EventListener... eventListeners) {
        isTrue(!configuratorToEventListenerMap.containsKey(configurator), "EventListeners are already configured for the " +
                "configurator: %s", configurator);
        configuratorToEventListenerMap.put(configurator, eventListeners);
    }

    @Override
    public String toString() {
        return "CompositeDataProvider{" +
                "providers=" + providers +
                '}';
    }
}
