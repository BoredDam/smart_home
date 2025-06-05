package commands.generalPurposeCommands;

import commands.Command;
import devices.Device;

public abstract class DeviceCommand implements Command {
    
    protected Device device;
    
    public DeviceCommand(Device device) {
        this.device = device;
    }

    @Override
    public abstract void run();

}
