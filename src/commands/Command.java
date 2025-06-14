package commands;

import devices.Device;

/**
 * Command is the common interface for every command of the system.
 * It's an implementation of the Command design pattern.
 * 
 * @author Damiano Trovato
 * @author Paolo Volpini
 */

public interface Command {
    public void run();
    /**
     * @param dev is the devices that has to run the command.
     */
    public void setDevice(Device dev);
}
