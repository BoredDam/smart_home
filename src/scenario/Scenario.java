package scenario;

import commands.Command;
import controller.SmartHomeController;
import devices.ObservableDevice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Scenario {
    public record ScheduledCommand(String devName, long delaySecs, long repeatSecs, Command cmd) {}
    private String name;
    private final List<ScheduledCommand> commandList = new ArrayList<>(); 
    private final Map<ObservableDevice, Boolean> devMonitor = new HashMap<>(); // decides which observable device should be considered

    public Scenario(String name) {
        this.name = name;
    }

    public String getName() { // needed, we don't want scenarios with duplicated names
        return name;
    }

    public void enableDeviceMonitoring(ObservableDevice device) {
        devMonitor.put(device, true);
    }

    public void changeName(String newName) {
        name = newName;
    }

    public void disableDeviceMonitoring(ObservableDevice device) {
        devMonitor.put(device, false);
    }

    public void addCommand(String devName, long delaySecs, long repeatSecs, Command cmd) {
        commandList.add(new ScheduledCommand(devName, delaySecs, repeatSecs, cmd));
    }

    public void removeCommand(int index) {
        try {
            commandList.remove(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid index.");
        }
    }

    public String commandListToString() {
        int[] index = new int[1];
        return commandList.stream().map(
            (rec) -> (index[0]++ +" \t" + rec.devName + "\t" + rec.cmd.getClass().getSimpleName() + 
                      "\tActivation after: " + rec.delaySecs + "s\tRepeats every:" + rec.repeatSecs + "s")
        ).collect(Collectors.joining("\n"));
        // oh my god. This is so fucking stupid. What the fuck?
        // Why the fuck does a normal index stop working BUT A VALUE IN A SINGLE ARRAY DOESN'T?
    }
    /* 
    public Map<ObservableDevice, Boolean> getMonitoredDevices() { 
        return devMonitor;
    }
        */

    // should implement logic of command deletion in some way...
    // we just skip this and let the user scenario manage it

    public void apply(SmartHomeController controller) {
        // method is no longer broken and the solution is functional
        commandList.forEach((rec) -> { controller.scheduleCommand(rec.devName, rec.delaySecs, rec.repeatSecs, rec.cmd); });

        // this works fine i guess, ignore the warning
        devMonitor.forEach((dev, mon) -> {controller.setDeviceMonitoring(dev, mon); } );
        System.out.println("Scenario " + name + " has been applied to the controller!");
    }
}

