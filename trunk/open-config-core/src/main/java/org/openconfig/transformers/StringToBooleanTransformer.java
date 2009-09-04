package org.openconfig.transformers;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class StringToBooleanTransformer implements Transformer<String, Boolean>{

    public Boolean transform(String source) {
        return Boolean.parseBoolean(source);
    }
    
}
