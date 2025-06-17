package scenario;

import commands.Command;
import controller.SmartHomeController;
import devices.ObservableDevice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Scenario is a class that offers a way to encapsulate a list of commands
 * that the system can execute (all together or in different scheduled times) in any given moment.  
 * It also allows the user to set a monitoring state for each observable device.
 * @author Paolo Volpini
 * @author Damiano Trovato
 */
public class Scenario {
    public record ScheduledCommand(String devName, long delaySecs, long repeatSecs, Command cmd) {}
    private String name;
    private final List<ScheduledCommand> commandList = new ArrayList<>(); 
    private final Map<ObservableDevice, Boolean> devMonitor = new HashMap<>(); // decides which observable device should be considered

    public Scenario(String name) {
        this.name = name;
    }

    // needed, we don't want scenarios with duplicated names
    /**
     * @return the name of the Scenario.
     */
    public String getName() { 
        return name;
    }

    /**
     * Specify a certain monitoring-state for a given device in the current instance
     * of Scenario
     * @param device the device whose the user wants to choose the monitoring state
     * @param enable the specified monitoring state 
     */
    public void setDeviceMonitoring(ObservableDevice device, boolean enable) {
        devMonitor.put(device, enable);
    }

    /**
     * @param newName the name the user wants to set for the current instance of Scenario
     */
    public void changeName(String newName) {
        name = newName;
    }

    /**
     * Add a command to the list of scheduled commands for this scenario.
     * @param devName is the name of the device
     * @param delaySecs if it's equal to 0, the command runs instantly. Otherwise, it will 
     *                  be executed after the specified amount of seconds
     * @param repeatSecs if it's equal to 0, the command runs once. Otherwise, it will
     *                   be executed at every specified amount of time.
     * @param cmd is the command that has to be executed
     */
    public void addCommand(String devName, long delaySecs, long repeatSecs, Command cmd) {
        commandList.add(new ScheduledCommand(devName, delaySecs, repeatSecs, cmd));
    }

    /**
     * Removes a command from the list of scheduled commands of this scenario.
     * @param index the index of the command to delete.
     */
    public void removeCommand(int index) {
        try {
            commandList.remove(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid index.");
        }
    }

    public int removeCommandByDevice(String devName) {
        int[] ret = new int[1];
        commandList.removeIf(rec -> {
            if (rec.devName.equals(devName)) {
                ret[0]++;
                return true;
            }
            return false;
        });
        return ret[0];
    }

    /**
     * @return a String containing a list of every command scheduled for the scenario.
     */
    public String commandListToString() {
        int[] index = new int[1];
        return commandList.stream()
            .map(rec -> String.format("%2d | Device: %-20s | Command: %-20s | Delay: %4ds | %-30s",
            index[0]++, rec.devName, rec.cmd.getClass().getSimpleName(), rec.delaySecs, (rec.repeatSecs > 0 ? "Repeats every " + rec.repeatSecs + "s" : "Does not repeat")
            ))
            .collect(Collectors.joining("\n"));
    }

    /**
     * Applies the scenario to a given controller.
     * @param controller controller who has to execute the scenario.
     */
    public void apply(SmartHomeController controller) {
        commandList.forEach((rec) -> { controller.scheduleCommand(rec.devName, rec.delaySecs, rec.repeatSecs, rec.cmd); });
        devMonitor.forEach((dev, mon) -> { controller.setDeviceMonitoring(dev, mon); } );
        System.out.println("Scenario " + name + " has been applied to the controller!");
    }
}

