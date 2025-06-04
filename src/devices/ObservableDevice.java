package devices;

import controller.Observer;

public abstract class ObservableDevice extends Device {
    protected Observer controllerObserving;
    public ObservableDevice(String name) {
        super(name);
    }
    
    public void attach(Observer master) {
        controllerObserving = master;
    }

    public void detach() {
        // logic of detaching. Will be precise when implementing the controller
    }
}
