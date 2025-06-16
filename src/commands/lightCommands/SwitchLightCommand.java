package commands.lightCommands;

public class SwitchLightCommand extends LightCommand {

    /**
     * Command to switch the light on or off.
     * The switch operates by toggling the state of the light.
     */
    public SwitchLightCommand() {
        super();
    }
    
    @Override
    public void run() {
        light.lightSwitch();
    }
}
