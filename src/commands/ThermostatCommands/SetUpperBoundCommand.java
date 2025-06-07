package commands.ThermostatCommands;

public class SetUpperBoundCommand extends ThermostatCommand {

    private final float bound; 
    public SetUpperBoundCommand(float bound) {
        super();
        this.bound = bound;
    }
    
    @Override
    public void run() {
        thermostat.setUpperBound(bound);
    }

}