package commands.ThermostatCommands;

import devices.thermostat.Thermostat;

public class SetLowerBoundCommand extends ThermostatCommand {

    private final float bound; 
    public SetLowerBoundCommand(Thermostat thermostat, float bound) {
        super(thermostat);
        this.bound = bound;
    }
    
    @Override
    public void run() {
        thermostat.setLowerBound(bound);
    }

}