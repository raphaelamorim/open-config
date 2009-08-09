package org.openconfig.jmx;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.prefs.Preferences;

/**
 * @todo extend Spring's test case
 * @author Richard L. Burton III
 */
public class JmxServerTestCase extends TestCase {

    private ApplicationContext context;

    public void testNothing() throws Exception {
        JmxClient client = new JmxClient(null, null);
        client.jmx();
    }

    @Override
    protected void setUp() throws Exception {
        context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
    }
}
