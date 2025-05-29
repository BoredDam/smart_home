package devices;

import events.Event;

public abstract class Device {
    protected PowerState pstate;
    protected String name;

    public Device(String name) {
        this.name = name;
        pstate = OffState.getInstance();
    }

    void update(Event event) {};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void turnOn() {
        pstate = pstate.turnOn();
    }
    public void turnOff() {
        pstate = pstate.turnOff();
    }

    public boolean isOn() {
        return pstate.isOn();
    }

    public boolean isOff() {
        return pstate.isOff();
    }
}
