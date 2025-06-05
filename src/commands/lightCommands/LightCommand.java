package commands.lightCommands;
import commands.Command;
import devices.light.Light;

public abstract class LightCommand implements Command {

    protected Light light;

    public LightCommand(Light light) {
        this.light = light;
    }

    public void setDevice(Light light) {
        this.light = light;
    }


    @Override
    public abstract void run();

}
