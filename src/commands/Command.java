package commands;

import devices.Device;

/**
 * Command is the common interface for every command of the system.
 * It's based on the Command design pattern by the GoF.
 * 
 * @author Damiano Trovato
 * @author Paolo Volpini
 */

public interface Command {
    /**
     * Makes the device (composed inside the command) run the command.
     */
    public void run();
    /**
     * @param dev is the device that has to run the command.
     */
    public void setDevice(Device dev);
}
