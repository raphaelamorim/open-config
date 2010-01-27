package org.openconfig.providers;

import junit.framework.TestCase;
import org.openconfig.core.OpenConfigContext;
import org.openconfig.providers.ast.ComplexNode;
import org.openconfig.providers.ast.NodeManager;
import org.openconfig.providers.ast.SimpleNode;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class YamlDataProviderTest extends TestCase {

    private NodeManager nodeManager = new NodeManager();
    
    private YamlDataProvider yamlDataProvider;

    public void testComplexPerson() {
        ComplexNode root = yamlDataProvider.getRoot();
        assertEquals("Richard", "name", root);
        assertEquals("Burton", "person.name", root);
        assertEquals(30, "person.age", root);
        assertEquals("SmartCode", "person.company.name", root);
        assertEquals("Dushy", "person.company.person.name", root);
        assertEquals(30, "person.company.person.age", root);
    }


    protected void assertEquals(String expected, String property, ComplexNode root) {
        assertEquals(expected, ((SimpleNode) nodeManager.find(property, root)).getValue());
    }
    protected void assertEquals(int expected, String property, ComplexNode root) {
        assertEquals(expected, ((SimpleNode) nodeManager.find(property, root)).getValue());
    }

    public void setUp(){
        yamlDataProvider = new YamlDataProvider();
        OpenConfigContext context = new OpenConfigContext() {
            public String getParameter(String name) {
                return "org/openconfig/providers/person";
            }

            public String getEnvironmentProperty(String name) {
                return null;
            }
        };
        yamlDataProvider.initialize(context);
        yamlDataProvider.reload();
    }
}
