package org.openconfig.transformers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openconfig.providers.ast.AbstractNode;
import org.openconfig.providers.ast.ComplexNode;
import org.openconfig.providers.ast.Person;
import org.openconfig.providers.ast.SimpleNode;

/**
 * @author Richard L. Burton III
 */
public class NodeTransformerTestCase {

    @Test
    public void verifyBasicTranformation() throws Exception {
        BeanToNodeTransformer transformer = new BeanToNodeTransformer();
        AbstractNode node = transformer.transform(new Person());
        assertEquals("Person", node.getName());
    }

    @Test
    public void verifyNestedTranformation() throws Exception {
        Person person = new Person("Richard", 29);
        person.setChild(new Person("Dushy", 30));

        BeanToNodeTransformer transformer = new BeanToNodeTransformer();
        ComplexNode personNode = transformer.transform(person);
        assertEquals("Person", personNode.getName());

        SimpleNode nameNode = (SimpleNode) personNode.getChild("name");
        assertEquals("Richard", nameNode.getValue());

        SimpleNode ageNode = (SimpleNode) personNode.getChild("age");
        assertEquals(29, ageNode.getValue());

        ComplexNode child = (ComplexNode) personNode.getChild("child");
        assertNotNull(child);

        SimpleNode name = (SimpleNode) child.getChild("name");
        assertEquals("Dushy", name.getValue());

        SimpleNode age = (SimpleNode) child.getChild("age");
        assertEquals(30, age.getValue());
    }

}

