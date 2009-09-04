package org.openconfig.providers;

import org.apache.log4j.Logger;
import org.openconfig.core.OpenConfigContext;
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
        ComplexNode root = new ComplexNode("root");
        Properties properties = new Properties();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            properties.load(new BufferedInputStream(fileInputStream));
        } catch (IOException e) {
            throw new RuntimeException("Unable to access properties file: " + file.getAbsolutePath());
        }finally{
            close(fileInputStream);
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
        String configurationFile = context.getParameter("interface");
        Assert.hasLength(configurationFile, "Could not get parameter 'interface' from context");
        URL configurationFileURL = getClass().getClassLoader().getResource(configurationFile + '.' + FILE_TYPE);
        Assert.notNull(configurationFileURL, "Cannot find the properties file: '%s.properties' in the root level of the classpath.", configurationFile);
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
