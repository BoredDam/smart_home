package commands.generalPurposeCommands;

public class TurnOffCommand extends DeviceCommand {

    @Override
    public void run() {
        device.turnOff();
    }

}
