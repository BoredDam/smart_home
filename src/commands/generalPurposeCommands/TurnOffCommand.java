package commands.generalPurposeCommands;

import devices.Device;

public class TurnOffCommand extends DeviceCommand {

    public TurnOffCommand(Device device) {
        super(device);
    }
    @Override
    public void run() {
        device.turnOff();
    }

}
