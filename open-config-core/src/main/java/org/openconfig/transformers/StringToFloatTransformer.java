package org.openconfig.transformers;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class StringToFloatTransformer implements Transformer<String, Float>{
    public Float transform(String source) {
        return Float.parseFloat(source);
    }
}
