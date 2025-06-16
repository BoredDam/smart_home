package commands.thermostatCommands;

public class SetUpperBoundCommand extends ThermostatCommand {

    private final float bound; 
    /**
     * Command to set the upper bound of the thermostat.
     * @param bound The new upper bound to set
     */
    public SetUpperBoundCommand(float bound) {
        super();
        this.bound = bound;
    }
    
    @Override
    public void run() {
        thermostat.setUpperBound(bound);
    }

}