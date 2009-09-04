package org.openconfig.transformers;

/**
 * todo: Handle unicode and such..
 * @author Richard L. Burton III - SmartCode LLC
 */
public class StringToCharacterTransformer implements Transformer<String, Character>{
    public Character transform(String source) {
        if( source.length() == 1){
            return source.charAt(0);
        }
        throw new IllegalArgumentException("The provided string isn't a single Character.");
    }
}
