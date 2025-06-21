package commands.airConditionerCommands;

/**
 * Command to set the target temperature of the air conditioner.
 * @author Paolo Volpini
 * @author Damiano Trovato
 */
public class SetTargetTemperatureCommand extends AirConditionerCommand {

    private final float targetTemp;
    
    /**
     * Command to set the target temperature of the air conditioner.
     * @param targetTemp the new target temperature to set
     */
    public SetTargetTemperatureCommand(float targetTemp) {
        super();
        this.targetTemp = targetTemp;
    }

    @Override
    public void run() {
        airConditioner.setTargetTemp(targetTemp);
    }
    
}
