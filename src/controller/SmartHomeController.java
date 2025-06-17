package controller;

import commands.Command;
import devices.Device;
import devices.ObservableDevice;
import environment.Environment;
import events.Event;
import events.EventManager;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import scenario.Scenario;
public class SmartHomeController implements Observer {
    // informations related to the scheduled commands and scenarios are memorized:
    // - inside ScheduledCommand, an immutable record which memorizes device name, command name, repetition and handle of task;
    // - inside ScheduledScenario, which memorizes scenario name, time of execution and handle of task.

    // scheduledScenarios records need to be mutable, therefore it's an inner class.
    
    private record ScheduledCommand(String devName, String commandName, boolean repeats, ScheduledFuture<?> handle) {}
    
    private class ScheduledScenario {
        String scenarioName;
        String time;
        ScheduledFuture<?> handle;

        ScheduledScenario(String scenarioName, Scenario scenario, String time, ScheduledFuture<?> handle) {
            this.scenarioName = scenarioName;
            this.time = time;
            this.handle = handle;
        }
    }

    private static SmartHomeController instance;
    private final Environment environment;
    private final EventManager eventManager;
    private final List<Device> device_list = new ArrayList<>(); // used to keep track of devices
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); // thread pool scheduler. Only one task at a time is runned
    private final List<ScheduledCommand> scheduledCommands = new ArrayList<>(); // list of scheduled commands
    private final Map<ObservableDevice, Boolean> listenedDevices = new HashMap<>(); // map of observable devices and their monitoring state
    private final List<ScheduledScenario> scheduledScenarios = new ArrayList<>();  // list of scheduled scenarios
    private final List<Scenario> userScenarios = new ArrayList<>();  // list of user-defined scenarios
    private final ScheduledFuture<?> cleaningTask;

    private SmartHomeController() {
        eventManager = EventManager.getInstance();
        cleaningTask = scheduler.scheduleAtFixedRate( () -> { scheduledCommands.removeIf((record) -> (record.handle.isDone())); }, 0, 30, TimeUnit.SECONDS);
        // this "daemon" cleans up the completed tasks. The frequency of 30 seconds should be sufficient
        // in order not to overload the system with task repetition
        environment = new Environment(this.device_list); // interacts with the environment
    }
    /**
     * Singleton pattern to ensure that there's only a single instance
     * of <code>SmartHomeController</code>.
     * @return the only SmartHomeController instance
     */
    public static SmartHomeController getInstance() {
        if(instance == null) {
            System.out.println("[SmartHomeController] Instance generated, running default config..."); 
            instance = new SmartHomeController();
        }
        return instance;
    }

    /**
     * Returns a string containing the user-defined scenarios.
     * @return a string with the names of all user-defined scenarios
     */
    public String scenariosListToString() {
        return userScenarios.stream().map(scenario -> ("| " + scenario.getName())).collect(Collectors.joining("\n"));
    }

    /**
     *  Checks if a device with @param scenarioname is already in the device list.
     *  @param scenarioName the name of the scenario to check
     *  @return true if the scenario name is already in use, false otherwise
     */
    public boolean scenarioNameCollision(String scenarioName) {
        return userScenarios.stream().anyMatch(scenario -> (scenario.getName().equals(scenarioName)));
    }

    /**
     * Returns a new scenario with the given name.
     * @param scenarioName the name of the scenario to return
     * @return <code>Scenario</code> instance with the given name, or null if the scenario does not exist.
     */

    private Scenario getScenarioFromName(String scenarioName) {
        return userScenarios.stream().filter(s -> s.getName().equals(scenarioName)).findFirst().orElse(null);
    }

    /**
     * Applies a scenario to the <code>SmartHomeController</code>.
     * This method will execute the commands of the scenario and update the monitoring states of observable devices.
     * @param scenarioName the name of the scenario to apply 
     */
    public void applyScenario(String scenarioName) {
        Scenario scenario = getScenarioFromName(scenarioName);
        if(scenario == null) {
            printMessage("Scenario " + scenarioName + " not found!");
            return;
        }
        scenario.apply(this);
    }
    /**
     * Removes a scenario from the user-defined scenarios.
     * @param scenarioName  the name of the scenario to remove
     */

    public void removeScenario(String scenarioName) {
        Scenario scenario = getScenarioFromName(scenarioName);
        if(scenario == null) {
            printMessage("Scenario " + scenarioName + " not found!");
            return;
        }
        userScenarios.remove(scenario);
        printMessage("Scenario " + scenarioName + " has been removed.");
    }

    /**
     * Adds a command to a scenario.
     * @param devName the name of the device on which the command will be executed
     * @param delaySecs the delay before the command is executed
     * @param repeatSecs the interval at which the command is repeated
     * @param cmd the command to execute
     * @param scenarioName the name of the scenario to add the command to
     */
    public void addCommandToScenario(String devName, long delaySecs, long repeatSecs, Command cmd, String scenarioName) {
        Scenario scenario = getScenarioFromName(scenarioName);
        if(scenario == null) {
            printMessage("Scenario " + scenarioName + " not found, cannot add command!");
            return;
        }
        scenario.addCommand(devName, delaySecs, repeatSecs, cmd);
    }

    /**
     * Returns a string representation of the commands in a scenario.
     * @param scenarioName the name of the scenario to get the commands from
     * @return a string with all commands in the scenario, or an empty string if the scenario does not exist
     */
    public String scenarioCommandsToString(String scenarioName) {
        Scenario scenario = getScenarioFromName(scenarioName);
        if(scenario == null) {
            printMessage("Scenario " + scenarioName + " not found!");
            return "";
        }
        return scenario.commandListToString();
    }

    public void removeCommandToScenario(String scenarioName, int index) {
        Scenario scenario = getScenarioFromName(scenarioName);
        if(scenario == null) {
            printMessage("Scenario " + scenarioName + " not found, cannot remove command!");
            return;
        }
        scenario.removeCommand(index);
    }

    /**
     * Adds a device for the <code>SmartHomeController</code> to monitor.
     * @param device to monitor
     * @param state of the monitoring
     */
    public void setDeviceMonitoring(ObservableDevice device, boolean state) {
        listenedDevices.put(device, state);
    }

    /**
     * Toggles the monitoring state for a given device.
     * @param device to change the monitoring state of
     */
    public void toggleDeviceMonitoring(ObservableDevice device) {
        listenedDevices.put(device, !listenedDevices.get(device));
        // toggles the state
    }

    /**
     * Gives back a string with the monitoring state of a device.
     * @param dev any type of device, observable or not.
     * @return <code>non-monitorable</code> if the device is not an <code>ObservableDevice</code>, 
     *         <code>monitored</code> if the device is monitored, <code>non-monitored</code> otherwise. 
     */
    public String printDevMonitoringState(Device dev) {
        if(dev instanceof ObservableDevice od){
            return (listenedDevices.get(od) ? "monitored" : "non-monitored");
        }
        return "non-monitorable";
    }

    /**
     * Returns a string representing a list of all scheduled scenarios.
     * @param prepend is a string added before each line printed
     * @return a string with every scheduled scenario of the <code>SmartHomeController</code>.
     */
    public String scheduledScenariosToString() {
        return scheduledScenarios.stream().map(rec -> rec.scenarioName + " scheduled at " + rec.time).collect(Collectors.joining("\n"));
    }


    /**
     * Prints out a message by the <code>SmartHomeController</code>.
     * @param message to print out.
     */
    private void printMessage(String message) {
        System.out.println("[SmartHomeController] " + message);
    }

    /**
     *  Cleans up the scheduled commands related to a device that has been deleted.
     * @param device the device that has been deleted
     */
    private void deletedDeviceCommandCleanup(Device device) {
        int[] idx = new int[1];
        idx[0] = 0;
        scheduledCommands.removeIf((record) -> {
            if(record.devName.equals(device.getName())) {
                record.handle.cancel(true);
                idx[0]++;
                return true;
            }
            return false;
        });
        System.out.println("[SmartHomeController] " + idx[0] + " command" + (idx[0] == 1  ? "" : "s") + " related to " + device.getName() + (idx[0] == 1 ? " has" : " have ") + " been cancelled.");
    }

    /**
     * Cleans up the scenarios related to a device that has been deleted.
     * The method will remove all commands in scenarios that reference the deleted device.
     * @param device the device that has been deleted
     */
    private void deletedDeviceScenarioCleanup(Device device) {
        int[] count = new int[1];
        count[0] = 0;
        userScenarios.forEach(scenario -> {
            count[0] += scenario.removeCommandByDevice(device.getName());
        });
        System.out.println("[SmartHomeController] Cleaned " + count[0] + " commands from scenarios.");
    }


    /**
     * Adds a device to the <code>SmartHomeController</code> device list.
     * @param device to add
     */
    public void addDevice(Device device) {
        if(device == null){
            printMessage("can't add a null device");
            return;
        }

        if(isIn(device.getName())) {
            printMessage("can't add two devices with the same name!");
            return;
        }
        device_list.add(device);
        printMessage(device.getName() + " just got registered to the SmartHomeController!");

        if(device instanceof ObservableDevice od) {
            listenedDevices.put(od, true);
            od.attach(this);
            printMessage(od.getName() + " is being monitored by controller!");
        }
    }


    /**
     * Updates the functionality of a device tracked by the <code>SmartHomeController</code>.
     * The method replaces the entry inside the device list with the updated one, if it exists.
     * @param devName the name of the device to update
     * @param updatedDevice the new device to replace the old one
     * @return true if the device was successfully updated, false if the device to update does not exist
     */
    public boolean updateFunctionality(String devName, Device updatedDevice) {
        Device oldDevice = getDeviceFromName(devName);
        if(oldDevice != null) {
            int idx = device_list.indexOf(oldDevice);
            if (idx != -1) {
                device_list.set(idx, updatedDevice);
                if(updatedDevice instanceof ObservableDevice od) {
                    listenedDevices.put(od, true);
                    listenedDevices.remove((ObservableDevice) oldDevice);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a specific device from the <code>SmartHomeController</code>
     * device list, if it exists.
     * @param device to remove
     */
    public boolean removeDevice(Device device) {
        if(device == null || !device_list.contains(device)) {
            return false;
        }
            
        if(device instanceof ObservableDevice d) {
            d.detach();
            listenedDevices.remove(d);
        }
        deletedDeviceCommandCleanup(device);
        deletedDeviceScenarioCleanup(device);
        device_list.removeIf(dev -> (dev.getName().equals(device.getName())));
        printMessage(device.getName() + " just got removed from the SmartHome Controller...");
        // it's necessary also to cleanup the scenarios commands that are related to the device
        return true;   
    }
    
    /**
     * Returns the string representing the device list of type <code>type</code> of the <code>SmartHomeController</code>
     * if <code>type</code> is empty, it returns all devices.
     * @param type the type of device to filter the list by. If empty, it returns all devices
     * @return a string with the device list
     */
    public String deviceListToString(String type) {
        if (type.isEmpty()) {
            return device_list.stream()
            .map(dev -> String.format("| %-20s %-60s %-4s %-15s",
                dev.getName(),
                dev.getType(),
                dev.isOn() ? "ON" : "OFF",
                printDevMonitoringState(dev)))
            .collect(Collectors.joining("\n"));
        }

        return device_list.stream()
            .filter(dev -> dev.getType().toLowerCase().contains(type.toLowerCase()))
            .map(dev -> String.format("| %-20s %-60s %-4s %-15s",
                dev.getName(),
                dev.getType(),
                dev.isOn() ? "ON" : "OFF",
                printDevMonitoringState(dev)))
            .collect(Collectors.joining("\n"));
    }
    
    /**
     * @param deviceName
     * @return true if an object that implements the <code>Device</code> interface with this
     * <code>deviceName</code> is in the device list of the <code>SmartHomeController</code>. 
     * Otherwise, it will return false.
     */
    public boolean isIn(String deviceName) {
        return device_list.stream().anyMatch(dev -> dev.getName().equals(deviceName));
    }

    /**
     * Triggers all of the commands of a given event.
     * @param event to react to.3
     */
    public void triggerEvent(Event event) {
        System.out.println("[SmartHomeController] Event " + event.getType() + " triggered!");
        device_list.forEach(dev -> event.getCommands(dev).forEach(cmd -> dev.performAction(cmd)));
    }

    /**
     * Updates the <code>SmartHomeController</code> with the event type specified by <code>eventType</code>
     * If the device is being monitored, it will trigger the event.
     * @param dev the device that has sent the notification
     * @param eventType the type of event that has been notified by the device
     */
    @Override
    public void update(ObservableDevice dev, String eventType) {
        if(listenedDevices.get(dev)){ 
           Event event = eventManager.getEvent(eventType); 
           if(event == null) {
                printMessage("The device " + dev.getName() + " has sent a notification that is not currently supported by any event");
                return;
            }
           triggerEvent(event);
        }   
    }

    /**
     * Returns the instance of a device registered to the 
     * SmartHomeController with the specified name
     * @param devName is the name of the device
     * @return the object which name is equal to <code>devName</code>
     */
    public Device getDeviceFromName(String devName) {
        return (Device) device_list.stream().filter(d -> devName.equals(d.getName())).findFirst().orElse(null); 
    }

    /**
     * Schedules a certain command that a device must run. 
     * @param devName is the name of the device
     * @param delaySecs if it's equal to 0, the command runs instantly. Otherwise, it will 
     *                  be executed after the specified amount of seconds
     * @param repeatSecs if it's equal to 0, the command runs once. Otherwise, it will
     *                  be executed at every specified amount of time.
     * @param cmd is the command that has to be executed
     */

    public void scheduleCommand(String devName, long delaySecs, long repeatSecs, Command cmd) {
        
        Device dev = getDeviceFromName(devName);
        if(dev == null) {
            printMessage("Device " + devName + " does not exist!");
            return;
        }

        ScheduledFuture<?> handle; 
        if(repeatSecs > 0) {
            handle = scheduler.scheduleAtFixedRate( () -> { dev.performAction(cmd); }, delaySecs, repeatSecs, TimeUnit.SECONDS);
        } else {
            handle = scheduler.schedule( () -> { dev.performAction(cmd); }, delaySecs, TimeUnit.SECONDS);
        }

        if (handle == null) { // handle creation error
            printMessage("Error in scheduling of command. Try again");
            return;
        }

        scheduledCommands.add(new ScheduledCommand(devName, cmd.getClass().getSimpleName(), repeatSecs > 0, handle)); 
            // the boolean is used to track if the task repeats or not
            // if a task is to be repeated, we can get the handler and we can kill it
    }

    /**
     * Returns a string with the time of command execution 
     * @param delaySecs is the delay expressed in seconds of the command execution
     * @return a string representing the time of command execution in the format "HH:mm"
     */
    private String elaborateDelay(long delaySecs) {
        LocalTime estimated = LocalTime.now();
        estimated = estimated.plus(delaySecs, ChronoUnit.SECONDS);
        return estimated.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    /**
     * Adds a new scenario to the list of user scenarios.
     * @param scenarioName the name of the scenario to add
     */
    public void addScenario(String scenarioName) {
        userScenarios.add(new Scenario(scenarioName));
        printMessage("Scenario " + scenarioName + " added to the list of user scenarios!");
    }

    /** 
     * Check if a scenario is scheduled
     * @param scenarioName is the name of the scenario to check
     *  @return true if the scenario is scheduled, false otherwise
    */
    public boolean isScenarioScheduled(String scenarioName) {
        return scheduledScenarios.stream().anyMatch(record -> record.scenarioName.equals(scenarioName));
    }

    /**
     * Schedules a scenario to be run after a certain delay.
     * @param scenarioName is the name of the scenario
     * @param scenario is the scenario to be run
     * @param delaySecs is the delay in seconds before the scenario is run
     */
    public void scheduleScenario(String scenarioName, long delaySecs) {
        Scenario scenario = getScenarioFromName(scenarioName);
        if(scenario == null) {
            printMessage("Scenario " + scenarioName + " not found, cannot schedule it!");
            return;
        }
        ScheduledFuture<?> handle = scheduler.scheduleAtFixedRate( () -> { scenario.apply(this); }, delaySecs, 86400, TimeUnit.SECONDS);
        if (handle == null) { // handle creation error
            printMessage("Error in scheduling of scenario. Try again");
            return;
        }
        
        String time = elaborateDelay(delaySecs);
        scheduledScenarios.add(new ScheduledScenario(scenarioName, scenario, time, handle));
    }

    /** 
     * Kills a scheduled scenario. The scenario is not removed from the list of available scenarios,
     * but will not be executed anymore unless scheduled again.
     * @param index is the index of the scenario to be killed
     */
    public void killScenario(String scenarioName) {
        ScheduledScenario rec = scheduledScenarios.stream()
            .filter(record -> record.scenarioName.equals(scenarioName))
            .findFirst()
            .orElse(null);
        if (rec == null) {
            System.err.println("Error in killing of scenario: scenario not found");
            return;
        }
        ScheduledFuture<?> handle = rec.handle;
        handle.cancel(true);
        if(handle.isCancelled()) {
            scheduledScenarios.removeIf(record -> record.scenarioName.equals(scenarioName));
        }
    }

    /**
     * Changes the name of a given scenario. The name won't be changed if a device with the same name as newName already exists.
     * @param oldName
     * @param newName
     */
    public void changeScenarioName(String oldName, String newName) {
        Scenario scenario = getScenarioFromName(oldName);

        if(scenario == null) {
            printMessage("Scenario " + oldName + " not found, cannot change name!");
            return;
        }
        // note: the check on the duplicate name is done in the User Interface
        scenario.changeName(newName);
        printMessage("Scenario " + oldName + " renamed to " + newName);
        scheduledScenarios.stream()
            .filter(record -> record.scenarioName.equals(oldName))
            .forEach(record -> record.scenarioName = newName);
    }

    /**
     * Sets the monitoring of a device for a certain scenario.
     * @param device to change the monitoring state of.
     * @param state the specified monitoring state.
     * @param scenarioName
     */
    public void setScenarioDeviceMonitoring(ObservableDevice device, boolean state, String scenarioName) {
        Scenario scenario = getScenarioFromName(scenarioName);
        if(scenario == null) {
            printMessage("Scenario " + scenarioName + " not found, cannot set device monitoring!");
            return;
        }
        scenario.setDeviceMonitoring(device, state);
        printMessage("Monitoring state for device " + device.getName() + " set to " + (state ? "monitored" : "non-monitored"));
    }

    // returns infos of the commands, hiding the handle
    // this also respects the order of the insertion, since it's an arrayList
    /**
     * @return a String containing every scheduled commands, with the device that will execute it, their time delay and their repeat-time.
     */
    public String scheduledCommandsToString() {
        int[] index = new int[1]; 
        return scheduledCommands.stream().filter(record -> (!record.handle.isDone())) // filters out completed tasks
            .map(record -> (index[0]++ + ") " + record.commandName + "\t" + record.devName + "\tNext execution: " + record.handle.getDelay(TimeUnit.SECONDS) + "s" +(record.repeats ? ("\trepeats") : ""))).collect(Collectors.joining("\n"));
    }

    // when printing the list, assure that the element are indexed starting from, and call 
    // the method with a decreased index. Index checking can be done, but exception is always caught
    public boolean killCommand(int index) {
        try {
            ScheduledFuture<?> handle = scheduledCommands.get(index).handle;
            handle.cancel(true);
            if(handle.isCancelled()) { 
                scheduledCommands.remove(index);
                return true;
            } 
            return false;
        } catch (IndexOutOfBoundsException e) { 
            System.err.println("Error in killing of command: index out of bounds");
        }
        return false;
    }

    /**
     * Flushes all the scheduled commands, cancelling them.
     * This method is used to clean up the scheduled commands when the controller is shut down.
     * Note that flushing the tasks is NOT a reversible process.
     */
    public void flushTasks() {
        scheduledCommands.forEach( record -> { record.handle.cancel(true); } );
        scheduledCommands.clear();
    }

    /**
     * Shuts down the controller, cleaning every scheduled commands.
     */
    public void shutdown() {
        try (scheduler) {
            flushTasks();
            cleaningTask.cancel(true);
        }
    }
    
    /**
     * Sets up the default events for the system.
     */
    public void setupDefaultEvents() {
        eventManager.setUpDefaultEvents(device_list);
    }

    /**
     * Calculates the temperature of the environment and updates the devices about it.
     */
    public void measureTemperatures() {
        environment.calculateTemperature();
    }

    /**
     * "Detects" the opening of a door by stimulating the environment.
     *  
     * @param doorName the name of the door to be opened.
     *                 If the name is "random", a random door will be opened.
     */
    public void detectOpeningDoor(String doorName) {
        if(doorName.equalsIgnoreCase("random")) {
            environment.actionOnRandomDoor(true);
        }
        else {
            environment.actionOnDoor(doorName, true);
        }
    }

    /**
     * "Detects" the opening of a door by stimulating the environment.
     *  
     * @param doorName the name of the door to be closed.
     *                 If the name is "random", a random door will be closed.
     */
    public void detectClosingDoor(String doorName) {
        if (doorName.equalsIgnoreCase("random")) {
            environment.actionOnRandomDoor(false);
        } else {
            environment.actionOnDoor(doorName, false);
        }
    }

    /**
     * Stimulates the presence detection of a camera in the environment.
     * @param cameraName the name of the camera to be stimulated.
     *                   If the name is "random", a random camera will be stimulated.
     */
    public void detectCameraPresence(String cameraName) {
        if(cameraName.equalsIgnoreCase("random")) {
            environment.randomCameraPresenceDetection();
        } else {
            environment.cameraPresenceDetection(cameraName);
        }
    }
}
