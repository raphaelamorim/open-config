package org.openconfig.providers;

import org.apache.log4j.Logger;
import org.openconfig.core.OpenConfigContext;
import org.openconfig.providers.ast.ComplexNode;
import org.openconfig.util.Assert;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import static org.openconfig.util.Assert.hasLength;
import static org.openconfig.util.Assert.isTrue;
import static org.openconfig.util.Assert.notNull;

/**
 * TODO: Finish this..
 * @author Richard L. Burton III - SmartCode LLC
 */
public class JsonDataProvider extends AbstractReloadableDataProvider{

    private Logger LOGGER = Logger.getLogger(JsonDataProvider.class);

    public static final String FILE_TYPE = "json";

    private File file;

    protected OpenConfigContext openConfigContext;

    private long lastModified;

    public void reload() {
        lastModified = file.lastModified();
        ComplexNode root = new ComplexNode();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (IOException e) {
            throw new RuntimeException("Unable to access JSON file: " + file.getAbsolutePath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(fileInputStream);
        }
        setRoot(root);

    }

    public boolean requiresReloading() {
        isTrue(file.lastModified() != 0, "Could not locate file: " + file.getAbsolutePath());
        return lastModified != file.lastModified();
    }

    public void initialize(OpenConfigContext context) {
        openConfigContext = context;
        String configurationFile = context.getParameter("interface");
        hasLength(configurationFile, "Could not get parameter 'interface' from context. If you're in Development Mode, please set the -Dopenconfig.dev=true");
        URL configurationFileURL = getClass().getClassLoader().getResource(configurationFile + '.' + FILE_TYPE);
        notNull(configurationFileURL, "Cannot find the properties file: '%s.xml' in the root level of the classpath.", configurationFile);
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
