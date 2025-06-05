package commands.doorCommands;
import commands.Command;
import devices.door.Door;

public abstract class DoorCommand implements Command {
    
    protected Door device;

    public DoorCommand(Door door) {
        device = door;
    }
    @Override
    abstract public void run();

    
}
