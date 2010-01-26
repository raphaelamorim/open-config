package org.openconfig.providers;

import org.apache.log4j.Logger;

import org.openconfig.providers.ast.ComplexNode;
import org.openconfig.providers.ast.NodeManager;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * A PropertiesDataProvider is backed by a simple properties file which allows developers to quickly define
 * key/value configurations. The following shows how to define a simple bean using the PropertiesDataProvider.
 * <br/>
 * <b>Java Bean</b>
 * <br/>
 * <pre>
 * public class Person{
 *      private String name = "Richard";
 *      private int age = 30;
 *
 * }
 * </pre>
 *
 * <b>Properties file</b>
 * <br>
 * <pre>
 * person.name=Richard
 * person.age=30
 * </pre>
 * <br/>
 * <br/>
 * Note: If a complex object graph is required, then it's recommend that you use either the <tt>YamlDataProvider</tt>
 * or the <tt>XmlDataProvider</tt> since they're better suited for representing hierarchical graphs.
 * @author Richard L. Burton III, SmartCode LLC
 */
public class PropertiesDataProvider extends FileDataProvider {

    private Logger LOGGER = Logger.getLogger(PropertiesDataProvider.class);

    /** The extension of the Properties file. */
    public static final String FILE_TYPE = "properties";

    /**
     * @see org.openconfig.providers.FileDataProvider#load(org.openconfig.providers.ast.ComplexNode, java.io.InputStream)
     */
    public void load(ComplexNode root, InputStream input) throws Exception {
        Properties properties = new Properties();
        BufferedInputStream bufferedInputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(input);
            properties.load(bufferedInputStream);
        } finally {
            close(bufferedInputStream);
        }

        NodeManager nodeManager = new NodeManager();
        for (Map.Entry entry : properties.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (value != null) {
                value = value.trim();
            }

            LOGGER.debug("Setting property '" + key + "' equal to '" +  value + "'.");
            nodeManager.setValue(root, key, value, true);
        }
    }

    /**
     * @see FileDataProvider#getFileType() 
     */
    protected String getFileType() {
        return FILE_TYPE;
    }

}
