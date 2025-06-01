package commands.lightCommands;

import devices.light.Light;

public class SwitchLight extends LightCommand {

    public SwitchLight(Light device) {
        super(device);
    }

    @Override
    public void run() {
        device.lightSwitch();
    }

}
