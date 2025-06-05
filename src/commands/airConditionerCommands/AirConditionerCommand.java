package commands.airConditionerCommands;
import commands.Command;

import devices.airConditioner.AirConditioner;

public class AirConditionerCommand implements Command {
    protected AirConditioner airConditioner;

    public AirConditionerCommand(AirConditioner airConditioner) {
        this.airConditioner = airConditioner;
    }
    abstract public void run();
}
