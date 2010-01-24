package org.openconfig.jmx.transformer;

import org.junit.Before;
import org.junit.Test;
import org.openconfig.jmx.builder.CompositeDataBuilder;
import org.openconfig.providers.ast.SimpleNode;

import javax.management.openmbean.*;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class SimpleTypeTransformerTest {

    private SimpleTypeTransformer simpleTypeTransformer;

    private CompositeDataBuilder builder;

    @Test
    public void verifyString() throws Exception {
        builder.addAttribute("name", "richard", SimpleType.STRING);
        SimpleNode simpleNode = simpleTypeTransformer.transformer("name", builder.build());
        assertNotNull(simpleNode);
        assertEquals("name", simpleNode.getName());
        assertEquals("richard", simpleNode.getValue());
    }

    @Test
    public void verifyInteger() throws Exception {
        builder.addAttribute("age", 30, SimpleType.INTEGER);
        SimpleNode simpleNode = simpleTypeTransformer.transformer("age", builder.build());
        assertNotNull(simpleNode);
        assertEquals("age", simpleNode.getName());
        assertEquals(30, simpleNode.getValue());
    }

    @Before
    public void setup() {
        builder = new CompositeDataBuilder("simple", "simple");
        simpleTypeTransformer = new SimpleTypeTransformer();
    }
}
