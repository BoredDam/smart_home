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

    public void setDeviceMonitoring(ObservableDevice device, boolean enable) {
        devMonitor.put(device, enable);
    }

    public void changeName(String newName) {
        name = newName;
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
    }

    public void apply(SmartHomeController controller) {
        commandList.forEach((rec) -> { controller.scheduleCommand(rec.devName, rec.delaySecs, rec.repeatSecs, rec.cmd); });
        devMonitor.forEach((dev, mon) -> {controller.setDeviceMonitoring(dev, mon); } );
        System.out.println("Scenario " + name + " has been applied to the controller!");
    }
}

