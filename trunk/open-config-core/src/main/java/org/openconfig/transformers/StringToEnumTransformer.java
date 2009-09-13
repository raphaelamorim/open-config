package org.openconfig.transformers;

/**
 * todo: fix the getValue().toString()...
 * @author Richard L. Burton III - SmartCode LLC
 */
public class StringToEnumTransformer implements Transformer<EnumValue, Enum> {

    public Enum transform(EnumValue source) {
        return Enum.valueOf(source.getEnumClass(), source.getValue().toString());
    }

}
