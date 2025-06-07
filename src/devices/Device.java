package devices;

import commands.Command;
import devices.powerState.OffState;
import devices.powerState.PowerState;
import events.Event;

public abstract class Device {
    protected PowerState pstate;
    protected String name;
    
    protected void printHeader() {
        System.out.print("[" + getName() + "] ");
    }

    public Device(String name) {
        this.name = name;
        pstate = OffState.getInstance();
    }

    public void update(Event event) {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void turnOn() {
        printHeader();
        pstate = pstate.turnOn();
    }

    public void turnOff() {
        printHeader();
        pstate = pstate.turnOff();
    }

    public boolean isOn() {
        return pstate.isOn();
    }

    public boolean isOff() {
        return pstate.isOff();
    }

    public void printState() {
        printHeader();
        System.out.println("State: " + (isOn() ? "on" : "off"));
    }

    public void performAction(Command cmd) {
        cmd.setDevice(this);
        pstate.runCommand(cmd);
    }
}
