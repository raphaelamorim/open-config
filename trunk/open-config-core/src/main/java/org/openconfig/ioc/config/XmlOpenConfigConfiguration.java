package org.openconfig.ioc.config;

/**
 * @author Richard L. Burton III
 */
public class XmlOpenConfigConfiguration extends AbstractOpenConfigConfiguration {

    public boolean accepts(String file) {
        return file.endsWith(".xml");
    }

    public void process(String file) throws Exception {

    }

}
