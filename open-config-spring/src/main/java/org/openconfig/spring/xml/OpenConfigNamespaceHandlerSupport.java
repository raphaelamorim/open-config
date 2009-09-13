package org.openconfig.spring.xml;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class OpenConfigNamespaceHandlerSupport extends NamespaceHandlerSupport {
    
    public void init() {
        registerBeanDefinitionParser("configurator", new ConfiguratorBeanDefinitionParser());
    }
}
