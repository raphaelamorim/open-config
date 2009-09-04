package org.openconfig.core.bean;

import org.openconfig.transformers.*;

import java.util.HashMap;
import java.util.Map;

/**
 * This class handles the conversion of string values to primative data types.
 * @author Richard L. Burton III - SmartCode LLC
 */
public class DataTypeManager {

    // TODO: Fix me...
    private Transformer NO_MATCH_TRANSFORMER = new Transformer(){
        public Object transform(Object source) {
            return source;
        }
    };

    private Map<Class, Transformer> transformers = new HashMap<Class, Transformer>();

    // todo: Handle Number and maybe ObjectToProxyTransformer?
    public DataTypeManager() {
        register(new StringToIntegerTransformer(), Integer.class, int.class);
        register(new StringToBooleanTransformer(), Boolean.class, boolean.class);
        register(new StringToDoubleTransformer(), Double.class, double.class);
        register(new StringToFloatTransformer(), Float.class, float.class);
        register(new StringToCharacterTransformer(), Character.class, float.class);
        register(new StringToLongTransformer(), Long.class, long.class);
    }

    public Transformer findTransformer(Class type){
        if(transformers.containsKey(type)){
            return transformers.get(type);
        }else{
            return NO_MATCH_TRANSFORMER;
        }
    }

    public void register(Transformer transformer, Class... classes){
        for(Class clazz : classes) {
        transformers.put(clazz, transformer);
        }
    }

}
