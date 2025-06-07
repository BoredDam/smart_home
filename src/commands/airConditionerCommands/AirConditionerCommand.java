package commands.airConditionerCommands;
import commands.Command;
import devices.Device;
import devices.airConditioner.AirConditioner;

public abstract class AirConditionerCommand implements Command {
    
    protected AirConditioner airConditioner;

    public AirConditionerCommand() {}
    
    @Override
    public void setDevice(Device dev) {
        airConditioner = (AirConditioner) dev;
    }
}
