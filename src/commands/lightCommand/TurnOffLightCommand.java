package commands.lightCommand;
import devices.Light;

public class TurnOffLightCommand extends LightCommand {

    public TurnOffLightCommand(Light device) {
        super(device);
    }

    @Override
    public void execute() {
        device.turnOff();
    }

}
