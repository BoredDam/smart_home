package commands;

import devices.Device;

public interface Command {
    public void run();
    public void setDevice(Device dev);
}
