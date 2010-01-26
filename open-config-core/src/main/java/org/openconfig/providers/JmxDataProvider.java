package org.openconfig.providers;

import org.openconfig.core.OpenConfigContext;
import org.openconfig.jmx.JmxClient;
import org.openconfig.jmx.transformer.OpenMBeanTranformer;
import org.openconfig.providers.ast.ComplexNode;

import javax.management.openmbean.CompositeData;
import java.util.Set;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class JmxDataProvider extends AbstractReloadableDataProvider {

    private JmxClient client;

    @Override
    public boolean requiresReloading() {
        return false;
    }

    @Override
    public void reload() {
    }

    public void initialize(OpenConfigContext context) {
        client = new JmxClient();
        client.setServiceURL("service:jmx:rmi://localhost/jndi/rmi://192.168.1.4:1099/openConfigConnector");
        client.setUsername("homer");
        client.setPassword("donut");
        client.connect();
        try {
            CompositeData object = (CompositeData) client.getConfigurations("Twitter Web");
            OpenMBeanTranformer transformer = new OpenMBeanTranformer();
            ComplexNode root = new ComplexNode();
            Set<ComplexNode> complexNodes = transformer.transform(object);
            for (ComplexNode node : complexNodes) {
                root.addChild(node);
            }
            setRoot(root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
