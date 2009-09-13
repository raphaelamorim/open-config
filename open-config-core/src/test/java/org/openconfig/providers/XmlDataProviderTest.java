package org.openconfig.providers;

import junit.framework.TestCase;
import org.openconfig.core.OpenConfigContext;
import org.openconfig.providers.ast.NodeManager;
import org.openconfig.providers.ast.SimpleNode;
import org.openconfig.providers.ast.AbstractNode;
import org.openconfig.providers.ast.ComplexNode;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class XmlDataProviderTest extends TestCase {

    public void testBasic() {
        OpenConfigContext context = new OpenConfigContext(){
            public String getParameter(String name) {
                return "xml-test";
            }

            public String getEnvironmentProperty(String name) {
                return null;
            }
        };
        XmlDataProvider dataProvider = new XmlDataProvider();
        dataProvider.initialize(context);
        dataProvider.reload();

        ComplexNode root = dataProvider.getRoot();
        NodeManager nodeManager  = new NodeManager();
        assertEquals("Burton", ((SimpleNode)nodeManager.find("person.name", root)).getValue());
        assertEquals("richard", ((SimpleNode)nodeManager.find("name", root)).getValue());

    }
}
