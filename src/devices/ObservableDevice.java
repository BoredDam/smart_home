package devices;

import controller.Observer;

/**
 * the Observable abstract class is the base of every device
 * of the system that can be "observed" by the SmartHomeController.
 * It implements the Device interface. 
 * 
 * @author Paolo Volpini
 * @author Damiano Trovato
 */

public abstract class ObservableDevice extends Device {
    protected Observer controllerObserving;

    public ObservableDevice(String name) {
        super(name);
        
    }
    
    /**
     * Sets an observer for this device.
     * @param master an object that implements the Observer interface.
     */
    public void attach(Observer master) {
        controllerObserving = master;
    }

    /**
     * Cancels the device registration to the observer.
     */
    public void detach() {
        controllerObserving = null;
    }

    /**
     * Notifies the observer about this device state.
     */
    public abstract void notifyObserver();

}
