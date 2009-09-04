package org.openconfig.transformers;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class StringToDoubleTransformer implements Transformer<String, Double> {
    public Double transform(String source) {
        return Double.parseDouble(source);
    }
}
