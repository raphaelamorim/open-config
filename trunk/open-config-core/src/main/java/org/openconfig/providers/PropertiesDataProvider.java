package org.openconfig.providers;

import java.io.IOException;
import java.io.Closeable;
import java.io.File;

import org.apache.log4j.Logger;
import org.openconfig.core.OpenConfigContext;

/**
 * @author Richard L. Burton III
 */
public class PropertiesDataProvider extends AbstractReloadableDataProvider {

    private Logger LOGGER = Logger.getLogger(PropertiesDataProvider.class);

    public static final String FILE_TYPE = "properties";

    private File file;

    private long lastModified;

    public PropertiesDataProvider() {
    }

    public boolean requiresReloading() {
        return lastModified != file.lastModified();
    }

    public void reload() {
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    public void initialize(OpenConfigContext context) {
        throw new UnsupportedOperationException("Not yet implemented!");
    }


    protected void close(Closeable closeable) {
        try {
            if (closeable != null)
                closeable.close();
        } catch (IOException ioe) {
            LOGGER.warn("Failed to close FileInputStream.");
        }
    }

}
