package devices.door;

import devices.Device;

public class Door extends Device{

    LockState lockState;

    public Door(String name) {
        super(name);
        lockState = OpenedState.getInstance();
    }
    
    public void open() {
        lockState = lockState.open();
    }

    public void close() {
        lockState = lockState.close();
    }

    public void unlock() {
        lockState = lockState.unlock();
    }

    public void lock() {
        lockState = lockState.lock();
    }
}
