package devices;

import events.Event;

public abstract class Device {
    
    protected String name;

    public Device(String name) {
        this.name = name;
    }

    void update(Event event) {};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
