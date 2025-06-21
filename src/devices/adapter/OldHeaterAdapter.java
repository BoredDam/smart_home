package devices.adapter;

import commands.Command;
import commands.generalPurposeCommands.TurnOffCommand;
import commands.generalPurposeCommands.TurnOnCommand;
import devices.Device;

/**
 * Adapter class for the OldHeater device.
 */
public class OldHeaterAdapter extends Device {
    
    private final OldHeater adaptee;

    public OldHeaterAdapter(String name) {
        super(name);
        adaptee = new OldHeater();
    }

    @Override
    public void turnOn() {
        printHeader();
        if(isOn()) {
            System.out.print("Already on!\n");
            return;
        }
        System.out.print("Turned On!\n");
        adaptee.boot();
    }

    @Override
    public void turnOff() {
        printHeader();
        if(isOff()) {
            System.out.print("Already off!\n");
            return;
        }
        System.out.print("Turned Off!\n");
        adaptee.shutdown();
    }

    @Override
    public boolean isOn() {
        return adaptee.getState();
    }

    @Override
    public boolean isOff() {
        return !(adaptee.getState());
    }

    @Override
    public String getBaseType() {
        return "OldHeater";
    }

    @Override
    public void performAction(Command cmd) {
        if (cmd instanceof TurnOnCommand) {
            turnOn();
        } else if (cmd instanceof TurnOffCommand) {
            turnOff();
        } else {
            System.out.println("Unsupported command.");
        }
    }
    
}
