package org.openconfig.jmx.transformer;

import org.junit.Before;
import org.junit.Test;
import org.openconfig.jmx.builder.CompositeDataBuilder;
import org.openconfig.providers.ast.ComplexNode;
import org.openconfig.providers.ast.SimpleNode;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.SimpleType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class CompositeDataTransformerTest {


    private CompositeDataTransformer compositeDataTransformer;

    @Test
    public void verifyComplex() {
        CompositeDataBuilder databaseConfiguration = new CompositeDataBuilder("db", "simple");
        databaseConfiguration.addAttribute("port", 8080, SimpleType.INTEGER);
        databaseConfiguration.addAttribute("url", "http://code.google.com/p/open-config", SimpleType.STRING);

        ComplexNode complexNode = compositeDataTransformer.transform("databaseConfiguration", databaseConfiguration.build());
        assertNotNull(complexNode);
        assertNotNull("databaseConfiguration", complexNode.getName());

        SimpleNode portNode = (SimpleNode) complexNode.getChild("port");
        assertEquals(8080, portNode.getValue());

        SimpleNode urlNode = (SimpleNode) complexNode.getChild("url");
        assertEquals("http://code.google.com/p/open-config", urlNode.getValue());

    }

    @Before
    public void setup() {
        compositeDataTransformer = new CompositeDataTransformer();
    }
}
