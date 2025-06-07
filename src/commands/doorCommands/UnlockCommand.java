package commands.doorCommands;

public class UnlockCommand extends DoorCommand {
    public UnlockCommand() {
        super();
    }

    @Override
    public void run() {
        device.unlock();
    }
}
