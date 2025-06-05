package commands.ThermostatCommands;

import commands.Command;
import devices.thermostat.Thermostat;

public abstract class ThermostatCommand implements Command {

    protected Thermostat thermostat;

    public ThermostatCommand(Thermostat thermostat) {
        this.thermostat = thermostat;
    }

    public abstract void run();
        
}
