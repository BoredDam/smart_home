package commands.lightCommands;


public class SwitchLight extends LightCommand {

    @Override
    public void run() {
        device.lightSwitch();
    }
}
