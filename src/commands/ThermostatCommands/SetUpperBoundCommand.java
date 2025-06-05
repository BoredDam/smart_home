package commands.ThermostatCommands;

import devices.thermostat.Thermostat;

public class SetUpperBoundCommand extends ThermostatCommand {

    private final float bound; 
    public SetUpperBoundCommand(Thermostat thermostat, float bound) {
        super(thermostat);
        this.bound = bound;
    }
    
    @Override
    public void run() {
        thermostat.setUpperBound(bound);
    }

}