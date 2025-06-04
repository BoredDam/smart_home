package devices.door;

import devices.Device;

public class Door extends Device{

    LockState lockState;
    public Door(String name) {
        super(name);
        lockState = OpenedState.getInstance();
    }
    
    public void open() {
        printHeader();
        lockState = lockState.open();
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
}
