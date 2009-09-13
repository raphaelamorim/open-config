import junit.framework.TestCase;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileInputStream;

import org.openconfig.providers.ast.ComplexNode;
import org.openconfig.providers.ast.NodeManager;
import org.openconfig.providers.ast.SimpleNode;
import org.xml.sax.SAXException;
import org.w3c.dom.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class DomXmlParserTest extends TestCase {

    public void testX() throws IOException, ParserConfigurationException, SAXException {
        File file = new File("open-config-core/src/test/resources/xml-test.xml");
        System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
        ComplexNode root = new ComplexNode();
        parse(root, new FileInputStream(file));
    }

    public void parse(ComplexNode root, InputStream input) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringComments(true);
        dbf.setIgnoringElementContentWhitespace(true);
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
                if ("person".equals(node.getNodeName())) {
                    isSimpleNode(node);
                }

                if(isSimpleNode(node) && node.getNodeType() == Node.ELEMENT_NODE){
                    nodeManager.setValue(root, node.getNodeName(), node.getTextContent(), true);
                }else{

                }

//                if (!isSimpleNode(node)) {
//                    processNode(nodeManager, root, nodeList.item(i));
//                } else {
//                    ComplexNode cpn = new ComplexNode(node.getNodeName());
//                    NamedNodeMap nnm = node.getAttributes();
//                    if (nnm != null) {
//                        for (int a = 0; a < nnm.getLength(); a++) {
//                            org.w3c.dom.Node attribute = nnm.item(a);
//                            SimpleNode attributeNode = new SimpleNode(attribute.getNodeName());
//                            attributeNode.setValue(attribute.getNodeValue());
//                            cpn.setValue(attributeNode);
//                        }
//                    }
//                    nodeManager.setValue(root, node.getNodeName(), node.getNodeValue(), true);
//                }
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

        if (element.hasAttributes() == false|| element.getTextContent() != null && element.getNodeType() == Node.ELEMENT_NODE) {
            return true;
        } else {
            return false;
        }
    }
}
