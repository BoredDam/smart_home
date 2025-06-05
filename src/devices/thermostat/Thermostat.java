package devices.thermostat;

import devices.ObservableDevice;
import devices.airConditioner.AirConditioner;
public class Thermostat extends ObservableDevice {
    private float measuredTemp; // just in case... i don't know how to manage the temps rn so we keep this in case of godmode
    private float lowerBound;
    private float upperBound;

    private AirConditioner pairedConditioner;

    public Thermostat(String name) {
        super(name);
    }
    
    public void pairAirConditioner(AirConditioner a) {
        pairedConditioner = a;
    }

    public float getTemperature() {
        return measuredTemp;
    } 

    public void setUpperBound(float upperBound) {
        this.upperBound = upperBound;

    }
    
    public void setLowerBound(float lowerBound) {
        this.lowerBound = lowerBound;
    }
}
