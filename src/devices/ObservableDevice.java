package devices;

import controller.Observer;
import events.Event;
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

    public void notify(Event event) {
        if(controllerObserving != null) controllerObserving.update(event);
        // null object? we'll see...
    }
}
