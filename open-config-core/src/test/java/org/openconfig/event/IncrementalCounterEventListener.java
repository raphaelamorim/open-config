package org.openconfig.event;

/**
 * @author Richard L. Burton III
 */
public class IncrementalCounterEventListener implements EventListener {

    private int count;

    public void onEvent(ChangeStateEvent event) {
        count++;
    }

    public int getCount() {
        return count;
    }
    
}
