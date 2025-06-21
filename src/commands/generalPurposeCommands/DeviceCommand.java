package commands.generalPurposeCommands;

import commands.Command;
import devices.Device;

public abstract class DeviceCommand implements Command {
    
    protected Device device;
    
    /**
     * Base class for commands that operate on a generic Device.
     * Every Device will support commands that extends this class.
     */
    public DeviceCommand() {
    }

    @Override
    public void setDevice(Device dev) {
        device = dev;
    }
}
