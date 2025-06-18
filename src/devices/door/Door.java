package devices.door;

import devices.ObservableDevice;

/**
 * Represents a door device that can be opened, closed, locked, and unlocked.
 * If the door goes from a locked state to an opened state, it notifies the controller of an intrusion.
 */
public class Door extends ObservableDevice {

    private LockState lockState;
    public Door(String name) {
        super(name);
        lockState = OpenedState.getInstance();
    }

    public void open() {
        printHeader();
        lockState = lockState.open(this);
    }

    public void close() {
        printHeader();
        lockState = lockState.close();
    }

    public void unlock() {
        printHeader();
        lockState = lockState.unlock();
    }

    public void lock() {
        printHeader();
        lockState = lockState.lock();
    }

    @Override
    public String getBaseType() {
        return "Door";
    }
    
    @Override
    public void notifyObserver() {
        if(isOn() && controllerObserving != null) controllerObserving.update(this, "Intrusion");
    }
    @Override
    public String getState() {
        return super.getState() + ", " + lockState.getState();
    }

}
