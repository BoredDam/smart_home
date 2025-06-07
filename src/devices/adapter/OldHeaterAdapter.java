package devices.adapter;

import devices.Device;

public class OldHeaterAdapter extends Device {
    
    private OldHeater adaptee;

    public OldHeaterAdapter(String name) {
        super(name);
    }

    @Override
    public void turnOn() {
        adaptee.boot();
    }

    @Override
    public void turnOff() {
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
}
