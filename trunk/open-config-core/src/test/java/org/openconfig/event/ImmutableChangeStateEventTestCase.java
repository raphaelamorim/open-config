package org.openconfig.event;

import junit.framework.TestCase;
import org.openconfig.providers.ast.ComplexNode;
import org.openconfig.providers.ast.Node;
import org.openconfig.providers.ast.Person;
import org.openconfig.providers.ast.SimpleNode;
import org.openconfig.transformers.BeanToNodeTransformer;

/**
 * @author Richard L. Burton III
 */
public class ImmutableChangeStateEventTestCase extends TestCase {

    private ComplexNode changeState;

    private BeanToNodeTransformer transformer = new BeanToNodeTransformer();

    public void testFindAttribute() {
        changeState.addChild(transformer.transform(new Person("Richard Burton", 30)));
        ImmutableChangeStateEvent event = new ImmutableChangeStateEvent(changeState);
        Node ageNode = event.find("Person.age");
        assertEquals("age", ageNode.getName());
        assertEquals(30, ((SimpleNode) ageNode).getValue());
    }

    public void testFindChild() {
        Person person = new Person();
        changeState.addChild(transformer.transform(person));

        ImmutableChangeStateEvent event = new ImmutableChangeStateEvent(changeState);
        Node node = event.find("Person.child.age");
        assertEquals("age", node.getName());
        assertEquals(31, ((SimpleNode) node).getValue());

        node = event.find("Person.child.name");
        assertEquals("name", node.getName());
        assertEquals("Dushyanth Inguva", ((SimpleNode) node).getValue());

    }

    @Override
    protected void setUp() throws Exception {
        changeState = new ComplexNode("root");
    }

}
