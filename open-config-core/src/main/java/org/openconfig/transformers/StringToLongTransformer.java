package org.openconfig.transformers;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class StringToLongTransformer implements Transformer<String, Long>{
    public Long transform(String source) {
        return Long.parseLong(source);
    }
}
