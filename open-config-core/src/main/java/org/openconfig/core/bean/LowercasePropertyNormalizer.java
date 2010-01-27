package org.openconfig.core.bean;

import org.apache.log4j.Logger;

import static java.lang.Character.toLowerCase;
import static java.lang.String.valueOf;

import com.google.inject.Singleton;

/**
 * @author Richard L. Burton III
 */
public class LowercasePropertyNormalizer implements PropertyNormalizer {

    public String normalize(String name) {
        if (name.length() > 1) {
            return toLowerCase(name.charAt(0)) + name.substring(1);
        }else{
            return valueOf(toLowerCase(name.charAt(0)));
        }
    }
    
}
