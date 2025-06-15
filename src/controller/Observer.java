package controller;

import devices.ObservableDevice;
/**
 * Interface for the class implementing the "Observer" design pattern.
 */

 
public interface Observer {
    void update(ObservableDevice dev, String event);
}
