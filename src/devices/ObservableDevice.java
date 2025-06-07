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
        controllerObserving = null;
    }

    public void notifyObserver() {
        if(controllerObserving != null) controllerObserving.update(this);
        // null object? we'll see...
    }
}
