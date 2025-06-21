package controller;

import devices.ObservableDevice;
/**
 * Interface for the class implementing the "Observer" design pattern.
 * The observer can be notified by the devices that observes.
 * 
 * @author Paolo Volpini
 * @author Damiano Trovato
 */

public interface Observer {
    /**
     * Reacts to a notification from an observed device.
     * @param dev is the device who sent the notification.
     * @param event is the name of the event thrown by the device.
     */
    void update(ObservableDevice dev, String event);
}
