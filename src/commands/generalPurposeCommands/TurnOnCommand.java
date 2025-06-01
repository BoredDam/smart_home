package commands.generalPurposeCommands;

public class TurnOnCommand extends DeviceCommand {

    @Override
    public void run() {
        device.turnOn();
    }

}
