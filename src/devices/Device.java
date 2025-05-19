package devices;

import events.Event;

public abstract class Device {
    
    private String name;

    Device(String name) {
        this.name = name;
    }

    void performAction(String action) {

    };

    void update(Event event) {
        
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
