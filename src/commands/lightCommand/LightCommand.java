package commands.lightCommand;
import commands.Command;
import devices.Light;

public abstract class LightCommand implements Command {

    Light device;

    LightCommand(Light device) {
        this.device = device;
    }

    @Override
    public abstract void execute();

}
