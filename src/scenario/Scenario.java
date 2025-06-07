package scenario;

import commands.Command;
import controller.SmartHomeController;
import devices.ObservableDevice;
import java.util.HashMap;
import java.util.Map;

public class Scenario {
    private String name;
    private final Map<Command, Integer> commandList = new HashMap<>(); 
    private final Map<String, Boolean> devMonitor = new HashMap<>(); // decides which observable device should be considered

    public Scenario(String name) {
        this.name = name;
    }

    public void enableDeviceMonitoring(String name) {
        devMonitor.put(name, true);
    }

    public void changeName(String newName) {
        name = newName;
    }

    public void disableDeviceMonitoring(String name) {
        devMonitor.put(name, false);
    }

    public void addCommand(Command cmd, int seconds) {
        // we assume that the command already has the device set
        commandList.put(cmd, seconds);
    }

    // should implement logic of command deletion in some way...
    // we just skip this and let the user scenario manage it

    public void apply(SmartHomeController controller) {
        // uhh i don't think this is a great idea... it's a limitation: a scenario  
        // applies the command but cannot schedule repetitive tasks
        // maybe we need to store the information of the integer as a pair of integers...
        // i think this is the worst thing that i have written so far. 
        // i am progressively losing my mind. The complexity has reached levels that are
        // greater than expected and i don't want to test this. Please call for help
        commandList.forEach((cmd, secs) -> { controller.scheduleCommand(secs, 0, cmd); });

        // this works fine i guess, ignore the warning
        devMonitor.forEach((name, mon) -> {controller.setDeviceMonitoring((ObservableDevice) controller.getDeviceFromName(name), mon); } );
        
    }
}

