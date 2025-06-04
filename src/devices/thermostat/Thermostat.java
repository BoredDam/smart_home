package devices.thermostat;

import devices.ObservableDevice;
public class Thermostat extends ObservableDevice {
    private float measuredTemp; // just in case... i don't know how to manage the temps rn so we keep this in case of godmode
    private float lowerBound;
    private float upperBound;
    
    public Thermostat(String name) {
        super(name);
    }
    
}
