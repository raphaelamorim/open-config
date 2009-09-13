package org.openconfig.providers;

import org.apache.log4j.Logger;
import org.openconfig.core.OpenConfigContext;
import static org.openconfig.ioc.OpenConfigModule.OPEN_CONFIG_DEVELOPMENT_FILE;
import org.openconfig.providers.ast.ComplexNode;
import org.openconfig.providers.ast.NodeManager;
import org.openconfig.util.Assert;

import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

/**
 * @author Richard L. Burton III
 */
public class PropertiesDataProvider extends AbstractReloadableDataProvider {

    private Logger LOGGER = Logger.getLogger(PropertiesDataProvider.class);

    public static final String FILE_TYPE = "properties";

    private File file;

    private long lastModified;

    protected OpenConfigContext openConfigContext;

    public PropertiesDataProvider() {
    }

    public boolean requiresReloading() {
        Assert.isTrue(file.lastModified() != 0, "Could not locate file: " + file.getAbsolutePath());
        return lastModified != file.lastModified();
    }

    public void reload() {
        ComplexNode root = new ComplexNode();
        Properties properties = new Properties();
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            fileInputStream     = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            properties.load(bufferedInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Unable to access properties file: " + file.getAbsolutePath());
        }finally{
            close(fileInputStream);
            close(bufferedInputStream);
        }

        NodeManager nodeManager = new NodeManager();
        for (Map.Entry entry : properties.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (value != null) {
                value = value.trim();
            }

            nodeManager.setValue(root, key, value, true);
        }

        setRoot(root);
    }

    /**
     * Uses the classpath to load the configuration file
     *
     * @param context
     */
    public void initialize(OpenConfigContext context) {
        openConfigContext = context;
        String configurationName = context.getParameter("interface");

        Assert.hasLength(configurationName, "Could not get parameter 'interface' from context");
        String configurationFileName = configurationName + '.' + FILE_TYPE;
        String customConfigFile = System.getProperty(OPEN_CONFIG_DEVELOPMENT_FILE);
        // TODO check if we are in local development mode
        if (customConfigFile != null && customConfigFile.trim().length() != 0) {
            LOGGER.info("Using custom application configuration file specified by system property:" + OPEN_CONFIG_DEVELOPMENT_FILE + " config file name: " + customConfigFile);
            configurationFileName = customConfigFile;
        }
        URL configurationFileURL = getClass().getClassLoader().getResource(configurationFileName);
        Assert.notNull(configurationFileURL, "Cannot find the properties file: %s in the classpath", configurationFileName);
        file = new File(configurationFileURL.getFile());
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
