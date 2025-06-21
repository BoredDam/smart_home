package commands.thermostatCommands;

import commands.Command;
import devices.Device;
import devices.thermostat.Thermostat;

public abstract class ThermostatCommand implements Command {

    protected Thermostat thermostat;

    /**
     * Base class for commands that operate on a Thermostat device.
     */
    public ThermostatCommand() {}
    
    @Override
    public void setDevice(Device dev) {
        thermostat = (Thermostat) dev;
    }
}
