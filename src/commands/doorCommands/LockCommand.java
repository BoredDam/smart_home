package commands.doorCommands;

public class LockCommand extends DoorCommand {
    public LockCommand() {
        super();
    }

    @Override
    public void run() {
        device.lock();
    }
}
