package org.openconfig.ioc.config;

import java.net.URL;

/**
 * This class uses a fallback strategy to locate the implementation classes. The first configuration and then the second
 * configuration are checked in order. 
 *
 * @author Dushyanth (Dee) Inguva
 */
public class MergingOpenConfigConfiguration implements OpenConfigConfiguration {
	
	private final OpenConfigConfiguration primaryOpenConfigConfiguration;
	private final OpenConfigConfiguration secondaryOpenConfigConfigurations;

    public MergingOpenConfigConfiguration(OpenConfigConfiguration primaryOpenConfigConfiguration, OpenConfigConfiguration defaultOpenConfigConfigurations) {
    	this.primaryOpenConfigConfiguration = primaryOpenConfigConfiguration;
    	this.secondaryOpenConfigConfigurations = defaultOpenConfigConfigurations;
    }

    public void process(URL file) {
    	throw new UnsupportedOperationException();
    }

	public Class<?> getClass(String name) {
		Class<?> clazz = primaryOpenConfigConfiguration.getClass(name);
		if(clazz == null) {
			clazz = secondaryOpenConfigConfigurations.getClass(name);
		}
		return clazz;
	}

	public boolean hasClass(String alias) {
		return primaryOpenConfigConfiguration.hasClass(alias) || secondaryOpenConfigConfigurations.hasClass(alias);
	}
}
