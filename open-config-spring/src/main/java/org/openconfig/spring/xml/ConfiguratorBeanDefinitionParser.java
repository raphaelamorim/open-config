package org.openconfig.spring.xml;

import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.w3c.dom.Element;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class ConfiguratorBeanDefinitionParser extends AbstractBeanDefinitionParser {

    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(SpringConfiguratorFactory.class, "newInstance");
        Class clazz = getClass(element.getAttribute("class"));
        factory.addConstructorArg(clazz);
        return factory.getBeanDefinition();
    }


    protected Class getClass(String clazzName) {
        try {
            return Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
