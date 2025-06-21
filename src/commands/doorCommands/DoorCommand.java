package commands.doorCommands;
import commands.Command;
import devices.Device;
import devices.door.Door;

public abstract class DoorCommand implements Command {
    
    protected Door device;

    /**
     * Base class for commands that operate on a Door device.
     */
    public DoorCommand() {}

    @Override
    public void setDevice(Device dev) {
        device = (Door) dev;
    }
}
