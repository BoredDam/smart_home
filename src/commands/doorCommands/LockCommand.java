package commands.doorCommands;

public class LockCommand extends DoorCommand {

    /**
     * Command to lock the door.
     */
    public LockCommand() {
        super();
    }

    @Override
    public void run() {
        device.lock();
    }
}
