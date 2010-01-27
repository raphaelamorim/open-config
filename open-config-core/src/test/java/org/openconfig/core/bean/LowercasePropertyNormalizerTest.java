package org.openconfig.core.bean;

import static org.junit.Assert.assertEquals;

/**
 * @author Richard L. Burton III
 */
public class LowercasePropertyNormalizerTest {

    private PropertyNormalizer propertyNormalizer = new LowercasePropertyNormalizer();

    public void verifyOneChar(){
        assertEquals("a", propertyNormalizer.normalize("A"));
    }

    public void verifyMultipleChars(){
        assertEquals("abc", propertyNormalizer.normalize("Abc"));
    }

    public void verifyUppercaseChars(){
        assertEquals("aBC", propertyNormalizer.normalize("ABC"));
    }

}
