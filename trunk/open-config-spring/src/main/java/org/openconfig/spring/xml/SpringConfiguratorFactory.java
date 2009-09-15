package org.openconfig.spring.xml;

import org.openconfig.factory.impl.DefaultConfiguratorFactory;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class SpringConfiguratorFactory {

    private Class clazz;

    public SpringConfiguratorFactory(Class clazz) {
        this.clazz = clazz;
    }
    
//    public <T> T newInstance(){
//        return new DefaultConfiguratorFactory().newInstance(clazz);
//    }
}
