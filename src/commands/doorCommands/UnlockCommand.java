package commands.doorCommands;

import devices.door.Door;

public class UnlockCommand extends DoorCommand {
    public UnlockCommand(Door door) {
        super(door);
    }

    @Override
    public void run() {
        device.unlock();
    }
}
