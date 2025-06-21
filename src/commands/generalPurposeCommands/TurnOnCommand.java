package commands.generalPurposeCommands;

public class TurnOnCommand extends DeviceCommand {

    /**
     * Command to turn on the device.
     */
    public TurnOnCommand() {
        super();
    }

    @Override
    public void run() {
        device.turnOn();
    }

}