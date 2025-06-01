package devices.light;

import devices.Device;

public class Light extends Device {

    public Light(String name) {
        super(name);
    }

    public void lightSwitch() {
        if(isOn()) {
            turnOff();
        } else {
            turnOn();
        }
    }
}
