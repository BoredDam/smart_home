package commands.doorCommands;

public class UnlockCommand extends DoorCommand {
    
    /**
     * Command to unlock the door.
     */
    public UnlockCommand() {
        super();
    }

    @Override
    public void run() {
        device.unlock();
    }
}
