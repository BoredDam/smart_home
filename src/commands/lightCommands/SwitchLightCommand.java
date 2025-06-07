package commands.lightCommands;

public class SwitchLightCommand extends LightCommand {

    public SwitchLightCommand() {
        super();
    }
    
    @Override
    public void run() {
        light.lightSwitch();
    }
}
