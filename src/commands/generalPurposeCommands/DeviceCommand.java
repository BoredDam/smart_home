package commands.generalPurposeCommands;

import commands.Command;
import devices.Device;

public abstract class DeviceCommand implements Command {
    
    protected Device device;
    
    public DeviceCommand() {
    }

    @Override
    public void setDevice(Device dev) {
        device = dev;
    }
}
