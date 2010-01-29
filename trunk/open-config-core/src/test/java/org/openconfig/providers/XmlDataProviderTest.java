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

    private XmlDataProvider dataProvider;


    private NodeManager nodeManager = new NodeManager();

    public void testNested() {
        ComplexNode root = configure("xml-test");
        assertEquals("Burton", "person.name", root);
        assertEquals("SmartCode LLC", "person.company.name", root);
        assertEquals("Dushy", "person.coworker.name", root);
        assertEquals("30", "person.coworker.age", root);
        assertEquals("richard", "name", root);
    }

    public void testComplexPerson() {
        ComplexNode root = configure("person");
        assertEquals("8080", "port", root);
        assertEquals("Burton", "person.name", root);
        assertEquals("30", "person.age", root);
        assertEquals("SmartCode", "person.company.name", root);
        assertEquals("Dushy", "person.company.person.name", root);
        assertEquals("30", "person.company.person.age", root);
    }

    protected void assertEquals(String expected, String property, ComplexNode root) {
        assertEquals(expected, ((SimpleNode) nodeManager.find(property, root)).getValue());
    }

    @Override
    protected void setUp() throws Exception {
        dataProvider = new XmlDataProvider();
    }

    protected ComplexNode configure(final String config) {
        OpenConfigContext context = new OpenConfigContext() {
            public String getParameter(String name) {
                return "org/openconfig/providers/" + config;
            }

            public String getEnvironmentProperty(String name) {
                return null;
            }
        };
        dataProvider.initialize(context);
        dataProvider.reload();
        return dataProvider.getRoot();
    }
}
