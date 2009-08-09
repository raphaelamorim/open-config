package org.openconfig;

import junit.framework.TestCase;
import static org.openconfig.ObjectFactory.getInstance;
import org.openconfig.factory.ConfiguratorFactory;

import java.util.prefs.Preferences;

/**
 * @author Richard L. Burton III
 */
public class ConfiguratorTestCase extends TestCase {

    private ConfiguratorFactory factory = getInstance().newConfiguratorFactory();

    private MyConfigurator configurator;

    public void testBasic() throws Exception {
        configurator = factory.newInstance(MyConfigurator.class);
        assertEquals("MyConfigurator.name", configurator.getName());
        assertEquals("MyConfigurator.age", configurator.getAge());
        assertEquals("MyConfigurator.age2", configurator.getAge2());
        assertEquals("MyConfigurator.person.child.name", configurator.getPerson().getChild().getName());
        assertEquals("MyConfigurator.person.name", configurator.getPerson().getName());
    }

    public void testNoAlias() throws Exception{
        configurator = factory.newInstance(MyConfigurator.class, false);
        assertEquals("name", configurator.getName());
        assertEquals("age", configurator.getAge());
        assertEquals("age2", configurator.getAge2());
        assertEquals("person.child.name", configurator.getPerson().getChild().getName());
        assertEquals("person.name", configurator.getPerson().getName());
    }


}
