package org.openconfig.providers;

import org.openconfig.core.OpenConfigContext;
import org.openconfig.util.Assert;
import org.openconfig.providers.ast.ComplexNode;
import org.openconfig.providers.ast.SimpleNode;
import org.apache.log4j.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.*;
import java.net.URL;

/**
 * This DataProvider constructs an AST node from an XML configuration file allowing
 * for developers to express their configuration in an XML format.
 *
 * <configurator>
 *  <person age="12" name="burton">
 *      <child age="14" name="Dushy"/>
 *  </person>
 *  <age>12</age>
 * </configurator>
 * <p/>
 * @author Richard L. Burton III - SmartCode LLC
 */
public class XmlDataProvider extends AbstractReloadableDataProvider {

    private Logger LOGGER = Logger.getLogger(XmlDataProvider.class);

    public static final String FILE_TYPE = "xml";

    private File file;

    protected OpenConfigContext openConfigContext;

    private long lastModified;

    public void reload() {
        ComplexNode root = new ComplexNode();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            parse(root, fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Unable to access XML file: " + file.getAbsolutePath());
        } catch (ParserConfigurationException e) {
            throw new IllegalArgumentException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(fileInputStream);
        }
        setRoot(root);
    }

    protected void parse(ComplexNode root, InputStream input) throws Exception {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlDoc = builder.parse(input);
        build(root, xmlDoc.getDocumentElement().getChildNodes());
    }

    private void build(ComplexNode root, NodeList nodes) {
        for (int n = 0; n < nodes.getLength(); n++) {
            Node node = nodes.item(n);
            String nodeName = node.getNodeName();

            if (node instanceof Element && node.hasAttributes()) {
                ComplexNode complexNode = new ComplexNode(nodeName);
                NamedNodeMap attrs = node.getAttributes();
                for (int i = 0; i < attrs.getLength(); i++) {
                    Attr attribute = (Attr) attrs.item(i);
                    complexNode.addChild(new SimpleNode(attribute.getName(), attribute.getValue()));
                    NodeList list = node.getChildNodes();
                    if (list.getLength() > 0) {
                        build(complexNode, list);
                    }
                }
                root.addChild(complexNode);
            } else if (node instanceof Element && nodeName != null) {
                SimpleNode attributeNode = new SimpleNode(nodeName, node.getTextContent());
                root.addChild(attributeNode);
            }
        }
    }

    public boolean requiresReloading() {
        Assert.isTrue(file.lastModified() != 0, "Could not locate file: " + file.getAbsolutePath());
        return lastModified != file.lastModified();
    }

    public void initialize(OpenConfigContext context) {
        openConfigContext = context;
        String configurationFile = context.getParameter("interface");
        Assert.hasLength(configurationFile, "Could not get parameter 'interface' from context. If you're in Development Mode, please set the -Dopenconfig.dev=true");
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
