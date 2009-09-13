package org.openconfig.providers;

import junit.framework.TestCase;
import org.openconfig.core.OpenConfigContext;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class XmlDataProviderTest extends TestCase {

    public void testBasic() {
        OpenConfigContext context = new OpenConfigContext(){
            public String getParameter(String name) {
                return "xml-test";
            }

            public String getEnvironmentProperty(String name) {
                return null;
            }
        };
        XmlDataProvider dataProvider = new XmlDataProvider();
        dataProvider.initialize(context);
        dataProvider.reload();
    }
}
