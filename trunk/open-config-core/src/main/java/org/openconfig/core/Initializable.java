package org.openconfig.core;

/**
 * This interface marks an object with an initializable method for being aware when it's ready to
 * initialize it's internal state.
 * @author Richard L. Burton III, SmartCode LLC
 */
public interface Initializable {

    /**
     * A call back method used to inform a subclass that it's time to initialize it's internal state.
     * @param context The OpenConfigContext handling any external configuration information.
     */
    void initialize(OpenConfigContext context);
    
}
