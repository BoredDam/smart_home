package commands;

import devices.Device;

/**
 * NullCommand is a class that implements the Command interface.
 * It encapsulates the "do-nothing" behaviour of a command that is
 * not coded in the system.
 * It's a clear example of use of the "NullObject" design pattern.
 * It is also a Singleton.
 */
public class NullCommand implements Command {
    private static NullCommand instance;

    private NullCommand() {}

    /**
     * Singleton pattern to ensure that there's only a single instance
     * of <code>NullCommand</code>.
     * @return the only NullCommand instance
     */
    public static NullCommand getInstance() {
        if(instance == null)
            instance = new NullCommand();
        return instance;
    }

    @Override
    public void run() {}

    @Override
    public void setDevice(Device dev) {}

}
