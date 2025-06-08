package devices.door;

import devices.ObservableDevice;

public class Door extends ObservableDevice{

    LockState lockState;
    public Door(String name) {
        super(name);
        lockState = OpenedState.getInstance();
    }
    
    public void open() {
        // this is very ugly and I'm not happy with the current implementation. As long as it works, I keep it untouched
        LockState previousState = lockState;
        printHeader();
        lockState = lockState.open();
        if(previousState instanceof LockedState && lockState instanceof OpenedState) 
            notifyObserver();
        
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
