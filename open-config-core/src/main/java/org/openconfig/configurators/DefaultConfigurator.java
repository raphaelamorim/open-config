package org.openconfig.configurators;

import org.openconfig.MutableConfigurator;
import org.openconfig.providers.DataProvider;

/**
 * @author Richard L. Burton III
 */
public class DefaultConfigurator implements MutableConfigurator {

    protected DataProvider dataProvider;

    public void setValue(String name, Object value) {
        throw new UnsupportedOperationException("This method is not yet implemented.");
    }

    public Object getValue(String key) {
        return dataProvider.getValue(key);
    }

    public DataProvider getDataProvider() {
        return dataProvider;
    }

    public void setDataProvider(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

}
