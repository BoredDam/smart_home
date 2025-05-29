package devices.light;

import devices.Device;

public class BaseLight extends Device implements Light {
/*  ho commentato la parte in virtù dell'interfaccia
    private boolean state;

    public BaseLight(String name) {
        super(name);
        state = false;
    }

    public boolean isOn() {
        return state;
    }

    public boolean isOff() {
        return !state;
    }

    public void turnOn() {
        this.state = true;
        printState();
    }

    public void turnOff() {
        this.state = false;
        printState();
        
    }

    public void lightSwitch() {
        this.state = !state;
        printState();
    }

    public void printState() {
        System.out.println(getName() + (isOn() ? "is on!" : "is off!" ) );
    }*/
    public BaseLight(String name) {
        super(name);
    }
    @Override
    public void lightSwitch() {
        if(isOn()) 
            turnOff(); 
        turnOn();
    }
    public void printState() {
        System.out.println(getName() + (isOn() ? " is on!" : " is off!" ) );
    }
}
