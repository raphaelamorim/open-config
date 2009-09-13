package org.openconfig.providers;

import org.openconfig.core.OpenConfigContext;
import org.openconfig.util.Assert;
import org.openconfig.providers.ast.ComplexNode;
import org.openconfig.providers.ast.NodeManager;
import org.openconfig.providers.ast.Node;
import org.openconfig.providers.ast.SimpleNode;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.util.Map;

/**
 * <configurator>
 * <person age="12" name="burton">
 * <child age="14" name="Dushy"/>
 * </person>
 * <age>12</age>
 * </configurator>
 *
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
            throw new RuntimeException("Unable to access properties file: " + file.getAbsolutePath());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            close(fileInputStream);
        }

        NodeManager nodeManager = new NodeManager();
//        for (Map.Entry entry : null.entrySet()) {
//            String key = (String) entry.getKey();
//            String value = (String) entry.getValue();
//            if (value != null) {
//                value = value.trim();
//            }
//
//            nodeManager.setValue(root, key, value, true);
//        }

        setRoot(root);
    }

    public void parse(ComplexNode root, InputStream input) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document dom = db.parse(input);

        Element rootNode = dom.getDocumentElement();
        NodeManager nodeManager = new NodeManager();
        NodeList nodeList = rootNode.getChildNodes();
        iterate(nodeManager, nodeList, root);
        System.out.println("root = " + root);

    }

    protected void iterate(NodeManager nodeManager, NodeList nodeList, ComplexNode root) {
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Node node = nodeList.item(i);
                if (!isSimpleNode(node)) {
                    processNode(nodeManager, root, nodeList.item(i));
                } else {
                    ComplexNode cpn = new ComplexNode(node.getNodeName());
                    NamedNodeMap nnm = node.getAttributes();
                    if (nnm != null) {
                        for (int a = 0; a < nnm.getLength(); a++) {
                            org.w3c.dom.Node attribute = nnm.item(a);
                            SimpleNode attributeNode = new SimpleNode(attribute.getNodeName());
                            attributeNode.setValue(attribute.getNodeValue());
                            cpn.setValue(attributeNode);
                        }
                    }
                    nodeManager.setValue(root, node.getNodeName(), node.getNodeValue(), true);
                }
            }
        }
    }

    protected void processNode(NodeManager nodeManager, ComplexNode root, org.w3c.dom.Node element) {
        String key = element.getNodeName();
        if (isSimpleNode(element)) {
            nodeManager.setValue(root, key, element.getNodeValue(), true);
        } else {
            ComplexNode complexNode = new ComplexNode(key);
            NodeList nodeList = element.getChildNodes();
            nodeManager.setValue(root, key, complexNode, true);
            iterate(nodeManager, nodeList, complexNode);
        }
    }

    protected boolean isSimpleNode(org.w3c.dom.Node element) {
        String value = element.getNodeValue();
        NamedNodeMap attributes = element.getAttributes();
        return attributes == null || attributes.getLength() == 0 && (element.getChildNodes().getLength() == 0 || element.getChildNodes().item(0).getNodeType() == org.w3c.dom.Node.TEXT_NODE);
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
