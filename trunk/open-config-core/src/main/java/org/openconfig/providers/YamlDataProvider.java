package org.openconfig.providers;

import org.apache.log4j.Logger;
import org.openconfig.providers.ast.ComplexNode;
import org.openconfig.providers.ast.SimpleNode;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;
import java.util.Set;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class YamlDataProvider extends FileDataProvider {

    private Logger LOGGER = Logger.getLogger(JsonDataProvider.class);

    public static final String FILE_TYPE = "yaml";

    public void load(ComplexNode root, InputStream input) {
        Yaml yaml = new Yaml();
        Iterable<Object> configuration = yaml.loadAll(input);

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
    }

    protected String getFileType() {
        return FILE_TYPE;
    }

    /**
     * Checks to see if the Object in question is a ComplexNode by inspecting the type and if it's a map, the size.
     * @param object The object in question.
     * @return true if the object is a ComplexNode, otherwise it's a SimpleNode.
     */
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

    /**
     * Used to construct a ComplexNode from a Map.
     * @param name The name of the node.
     * @param entry The attributes of the node.
     * @return The ComplexNode.
     */
    protected ComplexNode create(String name, Map<String, Object> entry) {
        LOGGER.debug("Creating a ComplexNode for '" + name + "'.");
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

}
