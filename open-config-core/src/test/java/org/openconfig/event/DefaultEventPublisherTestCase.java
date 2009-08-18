package org.openconfig.event;

import junit.framework.TestCase;

import java.util.Set;

import org.openconfig.providers.ast.Node;

/**
 * @author Richard L. Burton III
 */
public class DefaultEventPublisherTestCase extends TestCase {

    private EventPublisher eventPublisher;

    public void testSingleListener() {
        IncrementalCounterEventListener counter = new IncrementalCounterEventListener();
        eventPublisher.addListeners(counter);
        eventPublisher.publishEvent(new NullChangeStateEvent());
        assertEquals(1, counter.getCount());
    }

    public void testMultipleListener() {
        IncrementalCounterEventListener[] counters = {new IncrementalCounterEventListener(), new IncrementalCounterEventListener()};
        eventPublisher.addListeners(counters);
        eventPublisher.publishEvent(new NullChangeStateEvent());
        for(IncrementalCounterEventListener counter: counters){
            assertEquals(1, counter.getCount());
        }
    }

    @Override
    protected void setUp() throws Exception {
        eventPublisher = new DefaultEventPublisher();
    }

}
