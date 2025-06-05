package commands.doorCommands;
import devices.door.Door;

public class LockCommand extends DoorCommand {
    public LockCommand(Door door) {
        super(door);
    }

    @Override
    public void run() {
        device.lock();
    }
}
