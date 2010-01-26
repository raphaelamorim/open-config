package org.openconfig.jmx;

import org.apache.log4j.Logger;

import javax.management.Notification;
import javax.management.NotificationListener;

/**
 * @author Richard L. Burton III - SmartCode LLC
 */
public class ChangeStateNotificationListener implements NotificationListener {

    private Logger LOGGER = Logger.getLogger(ChangeStateNotificationListener.class);

    public void handleNotification(Notification notification, Object handback) {
        System.out.println("\nReceived service notification: ");
        System.out.println("\tNotification type: " + notification.getType());
        System.out.println("");
    }
}
