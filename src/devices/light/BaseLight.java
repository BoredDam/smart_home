package devices.light;

import devices.Device;

public class BaseLight extends Device implements Light {

    public BaseLight(String name) {
        super(name);
    }
    
    @Override
    public void lightSwitch() {
        if(isOn()) {
            turnOff(); 
        } else {

        }
        
    }

    public void printState() {
        System.out.println(getName() + (isOn() ? " is on!" : " is off!" ) );
    }
}
