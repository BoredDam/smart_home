package commands.lightCommands;
import devices.Light;

public class TurnOnLightCommand extends LightCommand {

    public TurnOnLightCommand(Light device) {
        super(device);
    }

    @Override
    public void execute() {
        device.turnOn();
    }
}
