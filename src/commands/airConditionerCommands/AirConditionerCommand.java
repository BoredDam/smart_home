package commands.airConditionerCommands;
import commands.Command;

import devices.airConditioner.AirConditioner;

public abstract class AirConditionerCommand implements Command {
    
    protected AirConditioner airConditioner;

    public AirConditionerCommand(AirConditioner airConditioner) {
        this.airConditioner = airConditioner;
    }
    abstract public void run();
}
