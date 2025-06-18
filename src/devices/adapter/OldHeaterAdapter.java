package devices.adapter;

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
        System.out.print("Turned On!\n");
        adaptee.boot();
    }

    @Override
    public void turnOff() {
        printHeader();
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
    
}
