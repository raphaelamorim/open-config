package org.openconfig.providers;

import org.apache.log4j.Logger;
import org.openconfig.core.OpenConfigContext;
import org.openconfig.providers.ast.ComplexNode;
import org.openconfig.providers.ast.SimpleNode;
import org.yaml.snakeyaml.Yaml;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.openconfig.util.Assert.hasLength;
import static org.openconfig.util.Assert.isTrue;
import static org.openconfig.util.Assert.notNull;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class YamlDataProvider extends AbstractReloadableDataProvider {

    private Logger LOGGER = Logger.getLogger(JsonDataProvider.class);

    public static final String FILE_TYPE = "yaml";

    private File file;

    protected OpenConfigContext openConfigContext;

    private long lastModified;

    public void reload() {
        lastModified = file.lastModified();
        ComplexNode root = new ComplexNode();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            Yaml yaml = new Yaml();
            Iterable<Object> configuration = yaml.loadAll(fileInputStream);

            for (Object o : configuration) {
                Map<String, Object> configurations = (Map<String, Object>) o;
                for (Map.Entry<String, Object> entry : configurations.entrySet()) {
                    if (isComplex(entry.getValue())) {
                        ComplexNode node = create(entry.getKey(), (Map<String, Object>) entry.getValue());
                        root.addChild(node);
                    } else {
                        root.addChild(create(entry));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to access JSON file: " + file.getAbsolutePath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(fileInputStream);
        }
        setRoot(root);

    }

    protected boolean isComplex(Object object) {
        if (object instanceof Map) {
            Map<String, Object> entry = (Map<String, Object>) object;
            if (entry.size() > 1) {
                return true;
            } else {
                Set<Map.Entry<String, Object>> entryNode = entry.entrySet();
                Map.Entry<String, Object> node = entryNode.iterator().next();
                return node.getValue() instanceof Map;
            }
        }
        return false;
    }

    protected ComplexNode create(String name, Map<String, Object> entry) {
        ComplexNode complexNode = new ComplexNode(name);

        for (Map.Entry<String, Object> node : entry.entrySet()) {
            Object value = node.getValue();
            if (value instanceof Map) {
                Map<String, Object> child = (Map<String, Object>) node.getValue();
                complexNode.addChild(create(node.getKey(), child));
            } else {
                SimpleNode simpleNode = new SimpleNode(node.getKey(), value);
                complexNode.addChild(simpleNode);
            }
        }
        return complexNode;
    }

    private SimpleNode create(Map.Entry<String, Object> node) {
        return new SimpleNode(node.getKey(), node.getValue());
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
