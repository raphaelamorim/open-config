package org.openconfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public interface UnSupportedConfigurator {

    List<String> getUnsupportedList();
    Set<String> getUnsupportedSet();
    Map<String, String> getUnsupportedMap();
    String[] getUnsupportedArray();
    int[] getUnsupportedIntArray();
    MyAnnotation getUnsupportedAnnotation();
    FinalObject getUnsupportedFinalObject();

}
