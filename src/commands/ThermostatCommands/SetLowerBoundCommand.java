package commands.thermostatCommands;

public class SetLowerBoundCommand extends ThermostatCommand {

    private final float bound; 

    /**
     * Command to set the lower bound of the thermostat.
     * @param bound The new lower bound to set
     */
    public SetLowerBoundCommand(float bound) {
        super();
        this.bound = bound;
    }
    
    @Override
    public void run() {
        thermostat.setLowerBound(bound);
    }

}