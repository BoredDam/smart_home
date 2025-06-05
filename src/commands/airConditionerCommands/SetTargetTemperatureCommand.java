package commands.airConditionerCommands;

import devices.airConditioner.AirConditioner;

public class SetTargetTemperatureCommand extends AirConditionerCommand {

    private final float targetTemp;
    
    public SetTargetTemperatureCommand(AirConditioner airConditioner, float targetTemp) {
        super(airConditioner);
        this.targetTemp = targetTemp;
    }

    @Override
    public void run() {
        airConditioner.setTargetTemp(targetTemp);
    }
    
}
