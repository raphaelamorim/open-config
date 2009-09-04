package org.openconfig.transformers;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class StringToIntegerTransformer implements Transformer<String, Number>{

    public Integer transform(String source) {
        return Integer.parseInt(source);
    }
    
}
