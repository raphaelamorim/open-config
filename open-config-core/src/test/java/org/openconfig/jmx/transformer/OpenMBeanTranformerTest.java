package org.openconfig.jmx.transformer;

import org.junit.Before;
import org.junit.Test;
import org.openconfig.jmx.builder.CompositeDataBuilder;
import org.openconfig.providers.ast.ComplexNode;
import org.openconfig.providers.ast.SimpleNode;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.SimpleType;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class OpenMBeanTranformerTest {

    private OpenMBeanTranformer openMBeanTranformer;

    @Test
    public void verify() {
        CompositeDataBuilder application = new CompositeDataBuilder("application", "simple");

        application.addChildCompositeData("databaseConfiguration", "db");
        application.addAttribute("port", 8080, SimpleType.INTEGER);
        application.addAttribute("url", "http://smartcodellc.com", SimpleType.STRING);

        application.addChildCompositeData("websiteConfiguration", "ws");
        application.addAttribute("logout", "http://logout.com", SimpleType.STRING);
        CompositeData compositeData = application.build();

        Set<ComplexNode> configurations = openMBeanTranformer.transform(compositeData);
        assertEquals(2, configurations.size());

        for (ComplexNode complexNode : configurations) {
            if ("databaseConfiguration".equals(complexNode.getName())) {
                assertEquals(8080, getAttribute(complexNode, "port"));
                assertEquals("http://smartcodellc.com", getAttribute(complexNode, "url"));
            } else {
                assertEquals("http://logout.com", getAttribute(complexNode, "logout"));
            }
        }
    }

    /**
     * TODO: Code smell.. Generics on the AbstractNode need to be fixed.
     * @param complexNode
     * @param attribute
     * @return
     */
    protected Object getAttribute(ComplexNode complexNode, String attribute) {
        SimpleNode node = (SimpleNode) complexNode.getChild(attribute);
        return node.getValue();
    }

    @Before
    public void setup() {
        openMBeanTranformer = new OpenMBeanTranformer();
    }
}
