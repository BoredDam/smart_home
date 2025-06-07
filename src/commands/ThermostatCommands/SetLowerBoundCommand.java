package commands.ThermostatCommands;

public class SetLowerBoundCommand extends ThermostatCommand {

    private final float bound; 
    public SetLowerBoundCommand(float bound) {
        super();
        this.bound = bound;
    }
    
    @Override
    public void run() {
        thermostat.setLowerBound(bound);
    }

}