package commands.generalPurposeCommands;

public class TurnOffCommand extends DeviceCommand {

    public TurnOffCommand() {
        super();
    }
    
    @Override
    public void run() {
        device.turnOff();
    }

}
