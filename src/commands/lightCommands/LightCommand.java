package commands.lightCommands;
import commands.Command;
import devices.Device;
import devices.light.Light;

public abstract class LightCommand implements Command {

    protected Light light;

    /**
     * Base class for commands that operate on a Light device.
     */
    public LightCommand() {
    }

    @Override
    public void setDevice(Device dev) {
        light = (Light) dev;
    }
}
