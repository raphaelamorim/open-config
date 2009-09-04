package org.openconfig.transformers;

import junit.framework.TestCase;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class DataTypeTransformerTest extends TestCase {

    public void testStringToInteger() {
        Transformer transformer = new StringToIntegerTransformer();
        assertEquals(100, transformer.transform("100"));
    }

    public void testStringToLong() {
        Transformer transformer = new StringToLongTransformer();
        assertEquals(100l, transformer.transform("100"));
    }

    public void testStringToDouble() {
        Transformer transformer = new StringToDoubleTransformer();
        assertEquals(100d, transformer.transform("100.0"));
    }

    public void testStringToFloat() {
        Transformer transformer = new StringToFloatTransformer();
        assertEquals(100.0f, transformer.transform("100.0"));
    }

    public void testStringToCharacter() {
        Transformer transformer = new StringToCharacterTransformer();
        assertEquals('1', transformer.transform("1"));
    }

    public void testStringToBoolean() {
        StringToBooleanTransformer transformer = new StringToBooleanTransformer();
        assertTrue(transformer.transform("true"));
        assertFalse(transformer.transform("false"));
    }

}
