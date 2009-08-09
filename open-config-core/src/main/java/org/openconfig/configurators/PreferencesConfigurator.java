package org.openconfig.configurators;

import org.openconfig.MutableConfigurator;

import java.util.prefs.Preferences;
import static java.util.prefs.Preferences.userRoot;

/**
 * @author Richard L. Burton III
 */
public class PreferencesConfigurator implements MutableConfigurator {

    private Preferences preferences = userRoot();

    public void setValue(String name, Object value) {
        preferences.putInt(name, 0);
    }

    /**
     * TODO: How to handle the proper invocation of the right method?
     */
    public Object getValue(String key) {
        return preferences.get(key, null);
    }
}
