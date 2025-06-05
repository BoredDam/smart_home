package commands.lightCommands;

import devices.light.Light;

public class SwitchLightCommand extends LightCommand {

    public SwitchLightCommand(Light light) {
        super(light);
    }
    
    @Override
    public void run() {
        light.lightSwitch();
    }
}
