package scenario;

import commands.Command;
import controller.SmartHomeController;
import devices.ObservableDevice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scenario {
    private static record ScheduledCommand(String devName, long delaySecs, long repeatSecs, Command cmd) {}
    private String name;
    private final List<ScheduledCommand> commandList = new ArrayList<>(); 
    private final Map<String, Boolean> devMonitor = new HashMap<>(); // decides which observable device should be considered

    public Scenario(String name) {
        this.name = name;
    }

    public String getName() { // needed, we don't want scenarios with duplicated names
        return name;
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

    public void addCommand(String devName, long delaySecs, long repeatSecs, Command cmd) {
        commandList.add(new ScheduledCommand(devName, delaySecs, repeatSecs, cmd));
    }

    // should implement logic of command deletion in some way...
    // we just skip this and let the user scenario manage it

    public void apply(SmartHomeController controller) {
        // method is no longer broken and the solution is functional
        commandList.forEach((rec) -> { controller.scheduleCommand(rec.devName, rec.delaySecs, rec.repeatSecs, rec.cmd); });

        // this works fine i guess, ignore the warning
        devMonitor.forEach((name, mon) -> {controller.setDeviceMonitoring((ObservableDevice) controller.getDeviceFromName(name), mon); } );
        
    }
}

