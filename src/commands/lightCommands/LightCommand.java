package commands.lightCommands;
import commands.Command;
import devices.light.Light;

public abstract class LightCommand implements Command {

    protected Light device;

    public void setDevice(Light device) {
        this.device = device;
    }


    @Override
    public abstract void run();

}
