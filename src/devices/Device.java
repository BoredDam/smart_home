package devices;

import commands.Command;
import devices.powerState.OffState;
import devices.powerState.PowerState;
import events.Event;

/**
 * Device is the common interface for every smart device of the system.
 * @author Paolo Volpini
 * @author Damiano Trovato
 */

public abstract class Device {
    protected PowerState pstate; // power state of the device
    protected String name; // name of the device
    
    /**
     * Prints out a tag with the device name. 
     * Example output: <code>[DeviceName]</code>
     */
    protected void printHeader() {
        System.out.print("[" + getName() + "] ");
    }

    public Device(String name) {
        this.name = name;
        pstate = OffState.getInstance();
    }
    
    /**
     * @return the object's class simple name.
     */    
    public String getType() {
        return this.getClass().getSimpleName();
    }
    
    /**
     * Reacts to a given event.
     * @param event the device has to react to.
     */
    public void update(Event event) {}

    /**
     * @return the name of the device.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name of the device.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Turns on the device.
     */
    public void turnOn() {
        printHeader();
        pstate = pstate.turnOn();
    }

    /**
     * Turns off the device.
     */
    public void turnOff() {
        printHeader();
        pstate = pstate.turnOff();
    }

    /**
     * @return <code>true</code> if the device is on. <code>false</code> otherwise.
     */
    public boolean isOn() {
        return pstate.isOn();
    }

    /**
     * @return <code>true</code> if the device is off. <code>false</code> otherwise.
     */
    public boolean isOff() {
        return pstate.isOff();
    }

    /**
     * Prints out the powerstate of the device.
     */
    public String getState() {
        return (isOn() ? "ON" : "OFF");
    }

    /**
     * Makes the device run a given command.
     * @param cmd is a command compatible with the device.
     */
    public void performAction(Command cmd) {
        cmd.setDevice(this);
        pstate.runCommand(cmd);
    }

    /**
     * Gives back a string with the base type of the device, ignoring eventual decorators.
     * @return a string with the name of the base type of the device.
     */
    public abstract String getBaseType();
    
}
