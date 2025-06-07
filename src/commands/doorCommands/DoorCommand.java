package commands.doorCommands;
import commands.Command;
import devices.Device;
import devices.door.Door;

public abstract class DoorCommand implements Command {
    
    protected Door device;

    public DoorCommand() {}
    @Override
    abstract public void run();

    @Override
    public void setDevice(Device dev) {
        device = (Door) dev;
    }
}
