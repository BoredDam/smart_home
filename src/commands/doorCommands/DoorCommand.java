package commands.doorCommands;
import commands.Command;
import devices.door.Door;

public abstract class DoorCommand implements Command {
    
    protected Door device;

    @Override
    abstract public void run();

    
}
