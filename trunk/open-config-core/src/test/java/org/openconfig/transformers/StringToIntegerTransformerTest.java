package org.openconfig.transformers;

import junit.framework.TestCase;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class StringToIntegerTransformerTest extends TestCase {

    public void testBasic() {
        StringToIntegerTransformer transformer = new StringToIntegerTransformer();
        assertEquals((Integer) 100, transformer.transform("100"));
    }
}
