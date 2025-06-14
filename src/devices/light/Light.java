package devices.light;

import commands.Command;
import commands.lightCommands.SwitchLightCommand;
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

    @Override
    public String getBaseType() {
        return "Light";
    }

    @Override
    public void performAction(Command cmd) {
        cmd.setDevice(this);
        if(cmd instanceof SwitchLightCommand switchLight) {
            switchLight.run();
        } else {
            pstate.runCommand(cmd);
        }
            
        
    }

}
