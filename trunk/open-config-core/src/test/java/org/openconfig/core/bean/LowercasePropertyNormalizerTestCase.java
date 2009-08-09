package org.openconfig.core.bean;

import junit.framework.TestCase;

/**
 * @author Richard L. Burton III
 */
public class LowercasePropertyNormalizerTestCase extends TestCase {

    PropertyNormalizer propertyNormalizer;

    public void testOneChar(){
        assertEquals("a", propertyNormalizer.normalize("A"));
    }


    public void testMultipleChars(){
        assertEquals("abc", propertyNormalizer.normalize("Abc"));
    }


    public void testUppercaseChars(){
        assertEquals("aBC", propertyNormalizer.normalize("ABC"));
    }

    @Override
    protected void setUp() throws Exception {
        propertyNormalizer = new LowercasePropertyNormalizer();
    }
}
