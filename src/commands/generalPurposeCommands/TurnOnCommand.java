package commands.generalPurposeCommands;

public class TurnOnCommand extends DeviceCommand {

    public TurnOnCommand() {
        super();
    }

    @Override
    public void run() {
        device.turnOn();
    }

}