package commands.ThermostatCommands;

import commands.Command;
import devices.Device;
import devices.thermostat.Thermostat;

public abstract class ThermostatCommand implements Command {

    protected Thermostat thermostat;

    public ThermostatCommand() {}
    
    @Override
    public void setDevice(Device dev) {
        thermostat = (Thermostat) dev;
    }
}
