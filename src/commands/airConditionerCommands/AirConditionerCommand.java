package commands.airConditionerCommands;
import commands.Command;
import devices.Device;
import devices.airConditioner.AirConditioner;

/** Base class for commands that operate on an AirConditioner device.
 * @author Paolo Volpini
 * @author Damiano Trovato
 */
public abstract class AirConditionerCommand implements Command {
    
    protected AirConditioner airConditioner;
    public AirConditionerCommand() {}
    
    @Override
    public void setDevice(Device dev) {
        airConditioner = (AirConditioner) dev;
    }
}
