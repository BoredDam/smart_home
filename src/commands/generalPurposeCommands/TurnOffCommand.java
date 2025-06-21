package commands.generalPurposeCommands;

public class TurnOffCommand extends DeviceCommand {

    /**
     * Command to turn off the device.
     */
    public TurnOffCommand() {
        super();
    }
    
    @Override
    public void run() {
        device.turnOff();
    }

}
