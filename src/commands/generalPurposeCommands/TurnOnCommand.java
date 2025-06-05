package commands.generalPurposeCommands;

import devices.Device;

public class TurnOnCommand extends DeviceCommand {

    public TurnOnCommand(Device device) {
        super(device);
    }

    @Override
    public void run() {
        device.turnOn();
    }

}
