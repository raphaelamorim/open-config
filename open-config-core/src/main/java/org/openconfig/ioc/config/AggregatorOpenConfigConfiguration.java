package org.openconfig.ioc.config;

import java.util.List;

/**
 * This class is designed to 'roll-up' or aggregrate the results from all of the
 * <tt>OpenConfigConfiguration</tt> classes in order they are provided through the
 * constructor.
 *
 * @author Richard L. Burton III
 */
public class AggregatorOpenConfigConfiguration extends AbstractOpenConfigConfiguration {

    public AggregatorOpenConfigConfiguration(List<OpenConfigConfiguration> openConfigConfigurations) {

    }

    public boolean accepts(String file) {
        throw new UnsupportedOperationException("This is wrong... there should be a 'central' view of the aggregated configuration values.");
    }

    public void process(String file) {
        throw new UnsupportedOperationException("This is wrong... there should be a 'central' view of the aggregated configuration values.");
    }
}
