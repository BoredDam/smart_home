package devices.adapter;

import devices.Device;
import events.Event;
import events.HighTemperatureEvent;
import events.LowTemperatureEvent;

public class DeviceAdapter extends Device {
    
    private OldHeater adaptee;

    public DeviceAdapter(String name) {
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
    public void update(Event event) {
        if(event instanceof HighTemperatureEvent) {
            turnOff();
        }
        else if (event instanceof LowTemperatureEvent) {
            turnOn();
        }
    }
}
