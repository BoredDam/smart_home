package devices.thermostat;

import devices.ObservableDevice;

public class Thermostat extends ObservableDevice {
    private float measuredTemp; // just in case... i don't know how to manage the temps rn so we keep this in case of godmode
    private float lowerBound;
    private float upperBound;
    boolean triggered; // the flag indicates if the thermostat has triggered an event
    // if the event is triggered, thermostat has already notified the observers
    //

    public Thermostat(String name) {
        super(name);
        triggered = false;
        lowerBound = 10;
        upperBound = 50;
    }

    public float getTemperature() {
        return measuredTemp;
    } 

    @Override
    public void notifyObserver() {
        if(isOn() && controllerObserving != null) {
            if(tooHot())
                controllerObserving.update(this, "HighTemperature");
            else if(tooCold())
                controllerObserving.update(this, "LowTemperature");
        }
    }
    public void measureTemperature(float temperature) {
        this.measuredTemp = temperature;
        if((tooHot() || tooCold()) && !triggered) {
            triggered = true;
            notifyObserver();
        } 
        else if(!tooHot() && !tooCold()) {
            triggered = false;
        }
        printHeader();
        System.out.println("Measured temperature: " + measuredTemp);
    }

    public boolean tooHot() {
        return measuredTemp > upperBound;
    }

    public boolean tooCold() {
        return measuredTemp < lowerBound;
    }

    public void setUpperBound(float upperBound) {
        printHeader();
        if(upperBound < 10 || upperBound > 50) {
            System.out.print("Asked for a value between 10 and 50, received out of bound. Setting to default value 50. ");
            this.upperBound = 50;
        }
        else if(upperBound < lowerBound) {
            System.out.print("Upper bound cannot be lower than lower bound. Setting to default value 50. ");
            this.upperBound = 50;
        }
        else {
            this.upperBound = upperBound;
        }
        System.out.println("Upper bound set to: " + this.upperBound);

    }
    
    public void setLowerBound(float lowerBound) {
        printHeader();
        if(lowerBound < 10 || lowerBound > 50) {
            System.out.print("Asked for a value between 10 and 50, received out of bound. Setting to default value 10. ");
            this.lowerBound = 10;
            return;
        }
        else if(lowerBound > upperBound) {
            System.out.print("Lower bound cannot be higher than upper bound. Setting to default value 10. ");
            this.lowerBound = 10;
        }
        else {
            this.lowerBound = lowerBound;
        }
        System.out.println("Lower bound set to: " + this.lowerBound);
    }

    @Override
    public String getBaseType() {
        return "Thermostat";
    }

    
}
