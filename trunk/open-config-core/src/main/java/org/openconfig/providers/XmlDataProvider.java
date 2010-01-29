package org.openconfig.providers;

import org.openconfig.providers.ast.ComplexNode;
import org.openconfig.providers.ast.SimpleNode;
import org.apache.log4j.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.*;

/**
 * This DataProvider constructs an AST node from an XML configuration file allowing
 * for developers to express their configuration in an XML format.
 * <p/>
 * <pre><configurator>
 * <person age="12" name="burton">
 * <child age="14" name="Dushy"/>
 * </person>
 * <age>12</age>
 * </configurator>
 * </pre>
 * <p/>
 *
 * @author Richard L. Burton III - SmartCode LLC
 */
public class XmlDataProvider extends FileDataProvider {

    private Logger LOGGER = Logger.getLogger(XmlDataProvider.class);

    public static final String FILE_TYPE = "xml";

    public void load(ComplexNode root, InputStream input) throws Exception {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        LOGGER.debug("Now parsing the input stream.");
        Document xmlDoc = builder.parse(input);
        Element rootNode = xmlDoc.getDocumentElement();
        processRootAttributes(root, rootNode);
        build(root, rootNode.getChildNodes());
    }

    protected String getFileType() {
        return FILE_TYPE;
    }

    /**
     * This function handles the processing of top level nodes on the root of the class.
     * @param root The root of the ASt
     * @param element The root node.
     */
    private void processRootAttributes(ComplexNode root, Element element){
        if(element.hasAttributes()){
            NamedNodeMap attributes = element.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                Attr attribute = (Attr) attributes.item(i);
                if(LOGGER.isDebugEnabled()){
                    LOGGER.debug("Adding an attribute '" +attribute.getName() + "' value='" + attribute.getValue() + "'.");
                }
                root.addChild(new SimpleNode(attribute.getName(), attribute.getValue()));
            }
        }
    }

    private void build(ComplexNode root, NodeList nodes) {
        for (int n = 0; n < nodes.getLength(); n++) {
            Node node = nodes.item(n);
            String nodeName = node.getNodeName();

            if (node instanceof Element && node.hasAttributes()) {
                LOGGER.debug("Creating an ComplexNode for '" + nodeName + "'.");
                ComplexNode complexNode = new ComplexNode(nodeName);
                NamedNodeMap attrs = node.getAttributes();
                for (int i = 0; i < attrs.getLength(); i++) {
                    Attr attribute = (Attr) attrs.item(i);
                    if(LOGGER.isDebugEnabled()){
                        LOGGER.debug("Adding an attribute '" +attribute.getName() + "' value='" + attribute.getValue() + "'.");
                    }
                    complexNode.addChild(new SimpleNode(attribute.getName(), attribute.getValue()));
                }
                NodeList list = node.getChildNodes();
                if (list.getLength() > 0) {
                    build(complexNode, list);
                }
                root.addChild(complexNode);
            } else if (node instanceof Element && nodeName != null) {
                LOGGER.debug("Creating an Simple for '" + nodeName + "'.");
                SimpleNode attributeNode = new SimpleNode(nodeName, node.getTextContent());
                root.addChild(attributeNode);
            }
        }
    }

}
