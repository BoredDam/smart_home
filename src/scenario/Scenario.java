package scenario;

import commands.Command;
import java.util.HashMap;
import java.util.Map;
import controller.SmartHomeController;

public class Scenario {
    private String name;
    private final Map<Command, Integer> commandList = new HashMap<>();
    private final Map<String, Boolean> devMonitor = new HashMap<>();

    public Scenario(String name) {
        this.name = name;
    }

    public void addDeviceMonitoring(String name) {
        devMonitor.put(name, true);
    }

    public void disableDeviceMonitoring(String name) {
        devMonitor.put(name, false);
    }

    public void addCommand(Command cmd, int seconds) {
        commandList.put(cmd, seconds);
    }

    // should implement logic of command deletion in some way...
    // we can work around this by looking for the name of the device
    // involved, but this requires the knowledge of the name 
    // of the device, which is not known from the command
    // otherwise we just skip this and let the user scenario manage it

    // if the user scenario manages it, we don't need a deleteCommand() to work

    public void apply(SmartHomeController controller) {
        //commandList.forEach();
    }
}

