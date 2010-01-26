package org.openconfig.providers;

import org.apache.log4j.Logger;
import org.openconfig.core.OpenConfigContext;
import org.openconfig.providers.ast.ComplexNode;

import java.io.*;
import java.net.URL;

import static org.openconfig.ioc.OpenConfigModule.OPEN_CONFIG_DEVELOPMENT_FILE;
import static org.openconfig.util.Assert.hasLength;
import static org.openconfig.util.Assert.isTrue;
import static org.openconfig.util.Assert.notNull;

/**
 * This abstract DataProvider is backed by a local file on disk. The intent is to make
 * creating local DataProviders that are file backed easier and allow the subclasses
 * to focus only the construction of the internal AST (Abstract Syntax Tree).
 * @author Richard L. Burton III - SmartCode LLC
 */
public abstract class FileDataProvider extends AbstractReloadableDataProvider {

    private Logger LOGGER = Logger.getLogger(FileDataProvider.class);

    /** The file which backs the FileDataProvider. */
    private File file;

    /** The last modified date/time used to determine when the file will be reloaded. */
    private long lastModified;

    /** The OpenConfigContext used to obtain any required context information used by the provider. */
    protected OpenConfigContext openConfigContext;

    /**
     * This method is invoked when the contents of the file backing the DataProvider must be consumed and
     * parsed into the internal AST representation.
     * @param root The root node of the AST.
     * @param input The InputStream of the file which doesn't need to be closed. The caller handles that.
     * @throws Exception Thrown in case there's a problem during the processing of the file.
     */
    public abstract void load(ComplexNode root, InputStream input) throws Exception;

    /**
     * This method is used to return the extension that is backing the FileDataProvider.
     * <tt>e.g. 'xml', 'yaml', 'properties'.</tt>
     * @return The file extension backing the FileDataProvider.
     */
    protected abstract String getFileType();

    /**
     * @see AbstractReloadableDataProvider#reload()
     */
    public void reload() {
        lastModified = file.lastModified();
        ComplexNode root = new ComplexNode();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            load(root, fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Unable to access '" + getFileType() + "' file: " + file.getAbsolutePath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(fileInputStream);
        }
        setRoot(root);
    }

    /**
     * @see org.openconfig.providers.AbstractReloadableDataProvider#requiresReloading()
     */
    public boolean requiresReloading() {
        isTrue(file.lastModified() != 0, "Could not locate file: " + file.getAbsolutePath());
        return lastModified != file.lastModified();
    }

    /**
     * @see org.openconfig.core.Initializable#initialize(org.openconfig.core.OpenConfigContext) 
     */
    public void initialize(OpenConfigContext context) {
        openConfigContext = context;
        String configurationFile = context.getParameter("interface");
        hasLength(configurationFile, "Could not get parameter 'interface' from context. If you're in Development Mode, please set the -Dopenconfig.dev=true");

        String configurationFileName = configurationFile + '.' + getFileType();
        String customConfigFile = System.getProperty(OPEN_CONFIG_DEVELOPMENT_FILE);
        if (customConfigFile != null && customConfigFile.trim().length() != 0) {
            LOGGER.info("Using custom application configuration file specified by system property:" + OPEN_CONFIG_DEVELOPMENT_FILE + " config file name: " + customConfigFile);
            configurationFileName = customConfigFile;
        }
        URL configurationFileURL = getClass().getClassLoader().getResource(configurationFileName);
        notNull(configurationFileURL, "Cannot find the properties file: '%s.%s' in the root level of the classpath.", configurationFile, getFileType());
        file = new File(configurationFileURL.getFile());
    }
    
    /**
     * This method is used for closing off any Closeable object.
     * @param closeable The object to be closed.
     */
    protected void close(Closeable closeable) {
        try {
            if (closeable != null)
                closeable.close();
        } catch (IOException ioe) {
            LOGGER.warn("Failed to close FileInputStream.");
        }
    }

    @Override
    public String toString() {
        return "{" +
                "file=" + file +
                ", lastModified=" + lastModified +
                '}';
    }
    
}
