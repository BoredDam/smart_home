package devices.light;

import devices.Device;

public class Light extends Device {

    private boolean state;

    public Light(String name) {
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
        getState();
    }

    public void turnOff() {
        this.state = false;
        getState();
        
    }

    public void lightSwitch() {
        this.state = !state;
        getState();
    }

    public void getState() {
        System.out.println(getName() + (isOn() ? "is on!" : "is off!" ) );
    }
}
