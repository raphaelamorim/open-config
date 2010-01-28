package org.openconfig.core.bean;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Richard L. Burton III
 */
public class LowercasePropertyNormalizerTest {

    private PropertyNormalizer propertyNormalizer = new LowercasePropertyNormalizer();

    @Test
    public void verifyOneChar(){
        assertEquals("a", propertyNormalizer.normalize("A"));
    }

    @Test
    public void verifyMultipleChars(){
        assertEquals("abc", propertyNormalizer.normalize("Abc"));
    }

    @Test
    public void verifyUppercaseChars(){
        assertEquals("aBC", propertyNormalizer.normalize("ABC"));
    }

}
