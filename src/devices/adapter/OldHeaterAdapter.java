package devices.adapter;

import devices.Device;

public class OldHeaterAdapter extends Device {
    
    private final OldHeater adaptee;

    public OldHeaterAdapter(String name) {
        super(name);
        adaptee = new OldHeater();
    }

    @Override
    public void turnOn() {
        printHeader();
        adaptee.boot();
    }

    @Override
    public void turnOff() {
        printHeader();
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
