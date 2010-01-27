package org.openconfig.event;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.openconfig.providers.ast.ComplexNode;
import org.openconfig.providers.ast.AbstractNode;
import org.openconfig.providers.ast.Person;
import org.openconfig.providers.ast.SimpleNode;
import org.openconfig.transformers.BeanToNodeTransformer;

import static org.junit.Assert.assertEquals;

/**
 * @author Richard L. Burton III
 */
public class ImmutableChangeStateEventTestCase {

    private ComplexNode changeState;

    private BeanToNodeTransformer transformer = new BeanToNodeTransformer();

    @Test
    public void verifySuccessfulFindAttribute() {
        changeState.addChild(transformer.transform(new Person("Richard Burton", 30)));
        ImmutableChangeStateEvent event = new ImmutableChangeStateEvent(changeState);
        AbstractNode ageNode = event.find("Person.age");
        assertEquals("age", ageNode.getName());
        assertEquals(30, ((SimpleNode) ageNode).getValue());
    }

    @Test
    public void verifySuccessfulFindChild() {
        Person person = new Person();
        changeState.addChild(transformer.transform(person));

        ImmutableChangeStateEvent event = new ImmutableChangeStateEvent(changeState);
        AbstractNode node = event.find("Person.child.age");
        assertEquals("age", node.getName());
        assertEquals(31, ((SimpleNode) node).getValue());

        node = event.find("Person.child.name");
        assertEquals("name", node.getName());
        assertEquals("Dushyanth Inguva", ((SimpleNode) node).getValue());

    }

    @Before
    public void setUp() throws Exception {
        changeState = new ComplexNode("root");
    }

}
