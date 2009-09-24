package org.openconfig.event;

import java.util.List;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class PropertyDelta {

    private List<String> propertyPath;

    private Object oldValue;
    private Object newValue;

}
