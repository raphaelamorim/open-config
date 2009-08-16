package org.openconfig.providers;

/**
 * @author Richard L. Burton III
 */
public abstract class AbstractReloadableDataProvider extends AbstractDataProvider {

    /**
     * Overrides AbstractDataProvider#getValue(String) to add logic that delegates
     * the checking and reloading of the data backing the DataProvider.
     * @param property The property to load.
     * @return The value for the property.
     */
    public Object getValue(String property) {
        boolean reload = requiresReloading();
        if (reload) {
            reload();
        }
        return super.getValue(property);
    }

    public abstract boolean requiresReloading();

    public abstract void reload();

}
