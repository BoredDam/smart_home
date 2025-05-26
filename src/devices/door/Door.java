package devices.door;

import devices.Device;

public class Door extends Device{

    LockState lockState;

    public Door(String name) {
        super(name);
        lockState = OpenedState.getInstance();
    }
    
}
