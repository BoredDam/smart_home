package commands.airConditionerCommands;

public class SetTargetTemperatureCommand extends AirConditionerCommand {

    private final float targetTemp;
    
    public SetTargetTemperatureCommand(float targetTemp) {
        super();
        this.targetTemp = targetTemp;
    }

    @Override
    public void run() {
        airConditioner.setTargetTemp(targetTemp);
    }
    
}
