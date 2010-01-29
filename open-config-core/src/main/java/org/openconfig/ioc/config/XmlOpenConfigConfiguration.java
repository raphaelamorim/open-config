package org.openconfig.ioc.config;

import java.net.URL;

/**
 * @author Richard L. Burton III
 */
public class XmlOpenConfigConfiguration extends AbstractOpenConfigConfiguration {

    public boolean accepts(String file) {
        return file.endsWith(".xml");
    }

    public void process(URL file) {
        throw new UnsupportedOperationException("This method is not yet supported");
    }

}
