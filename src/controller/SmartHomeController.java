package controller;

import commands.Command;
import devices.Device;
import devices.ObservableDevice;
import devices.camera.Camera;
import devices.door.Door;
import devices.thermostat.Thermostat;
import environment.Environment;
import events.Event;
import events.EventManager;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
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
    private record ScheduledCommand(String devName, String commandName, boolean repeats, ScheduledFuture<?> handle) {}
    public record ScheduledCommandInfos(String devName, String commandName, boolean repeats) {} // used to hide the handle from the user
    private record ScheduledScenario(String scenarioName, Scenario scenario, String time, ScheduledFuture<?> handle) {}

    private static SmartHomeController instance;
    private final Environment environment;
    private final EventManager eventManager;
    private final List<Device> device_list = new ArrayList<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final List<ScheduledCommand> scheduledCommands = new ArrayList<>();
    private final Map<ObservableDevice, Boolean> listenedDevices = new HashMap<>();
    private final List<ScheduledScenario> scheduledScenarios = new ArrayList<>();    

    private SmartHomeController() {
        eventManager = EventManager.getInstance();
        scheduler.scheduleAtFixedRate( () -> { scheduledCommands.removeIf((record) -> (record.handle.isDone())); }, 0, 30, TimeUnit.SECONDS);
        // this "daemon" cleans up the completed tasks. The frequency of 30 seconds should be perfect
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
     * Adds a device for the <code>SmartHomeController</code> to monitor.
     * @param device to monitor
     * @param state of the monitoring
     */
    public void setDeviceMonitoring(ObservableDevice device, boolean state) {
        listenedDevices.put(device, state);
    }

    public void toggleDeviceMonitoring(ObservableDevice device) {
        listenedDevices.put(device, !listenedDevices.get(device));
        // toggles the state
    }

    public String printDevMonitoringState(Device dev) {
        if(dev instanceof ObservableDevice od){
            return (listenedDevices.get(od) ? "monitored" : "non-monitored");
        }
        return "non-monitorable";
    }

    public String scheduledScenariosToString() {
        return scheduledScenarios.stream().map(rec -> rec.scenarioName + " scheduled at " + rec.time).collect(Collectors.joining("\n"));
    }

    private void printMessage(String message) {
        System.out.println("[SmartHomeController] " + message);
    }

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

    public boolean updateFunctionality(String devName, Device updatedDevice) {
        Device oldDevice = getDeviceFromName(devName);
        if(oldDevice != null) {
            int idx = device_list.indexOf(oldDevice);
            if (idx != -1) {
                device_list.set(idx, updatedDevice);
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
        device_list.removeIf(dev -> (dev.getName().equals(device.getName())));
        printMessage(device.getName() + " just got removed from the SmartHome Controller...");
        return true;
        
    }

    
    /**
     * Returns the string representing the device list of the <code>SmartHomeController</code>.
     */
    public String deviceListToString(String type) {
        if(type.isEmpty())
            return device_list.stream().map(dev -> ("| " + dev.getName() + "\t\t" + dev.getType() 
            + "\t" + (dev.isOn() ? "ON" : "OFF") + "\t" + printDevMonitoringState(dev)))
                .collect(Collectors.joining("\n"));

        return device_list.stream()
            .filter(dev -> dev.getType().contains(type))
            .map(dev -> ("| " + dev.getName() + "\t\t" + dev.getType() 
                    + "\t" + (dev.isOn() ? "ON" : "OFF") + "\t" + printDevMonitoringState(dev)))
            .collect(Collectors.joining("\n"));
    }
    
    /**
     * 
     * @param deviceName
     * @return true if an object that implements the <code>Device</code> interface with this
     * <code>deviceName</code> is in the device list of the <code>SmartHomeController</code>. 
     * Otherwise, it will return false.
     */
    public boolean isIn(String deviceName) {
        return device_list.stream().anyMatch(dev -> dev.getName().equals(deviceName));
    }

    public void triggerEvent(Event event) {
        System.out.println("[SmartHomeController] Event " + event.getType() + " triggered!");
        device_list.forEach(dev -> event.getCommands(dev).forEach(cmd -> dev.performAction(cmd)));
    }

    @Override
    public void update(ObservableDevice dev) {
        if(listenedDevices.get(dev)){ 
            switch(dev) {
                case Thermostat ts -> {
                    if(ts.tooHot()) {
                        triggerEvent(eventManager.getEvent("HighTemperature"));
                    } 
                    else if (ts.tooCold()) {
                        triggerEvent(eventManager.getEvent("LowTemperature"));
                    } 
                }
                case Camera _, Door _ -> { 
                    // the notification is sent by the door when the state changes from locked to opened 
                    // essentially, it means that the door was forced.
                    triggerEvent(eventManager.getEvent("Intrusion"));
                }
                default -> {
                    printMessage("The device " + dev.getName() + " has sent a notification that is not currently supported by any event");
                }
            }       
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

        ScheduledFuture<?> handle = null; 
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

    public void scheduleScenario(String scenarioName, Scenario scenario, long delaySecs) {
        ScheduledFuture<?> handle = scheduler.scheduleAtFixedRate( () -> { scenario.apply(this); }, delaySecs, 86400, TimeUnit.SECONDS);
        if (handle == null) { // handle creation error
            printMessage("Error in scheduling of scenario. Try again");
            return;
        }
        LocalTime estimated = LocalTime.now();
        estimated = estimated.plus(delaySecs, ChronoUnit.SECONDS);
        String time = estimated.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        scheduledScenarios.add(new ScheduledScenario(scenarioName, scenario, time, handle));
        // don't think we should repeat scenarios...
    }

    public List<Device> getViewOnlyDevices() {
        return Collections.unmodifiableList(device_list);
    }

    // returns infos of the commands, hiding the handle
    // this also respects the order of the insertion, since it's an arrayList

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

    public void flushTasks() {
        // the function clears the map and deletes every scheduled command. Be advised because 
        // the function DOES NOT INCLUDE ANY DOUBLE CHECK: ONCE CALLED, EVERY HANDLER IS CLEARED 
        // AND THERE IS NO WAY TO RECOVER THE COMMANDS
        scheduledCommands.forEach( record -> { record.handle.cancel(true); } );
        scheduledCommands.clear();
    }

    public void shutdown() {
        try (scheduler) {
            flushTasks();
        }
    }
    
    public void setupDefaultEvents() {
        eventManager.setUpDefaultEvents(device_list);
    }

    public void measureTemperatures() {
        environment.calculateTemperature();
    }

    public void detectOpeningDoor(String doorName) {
        if(doorName.equalsIgnoreCase("random")) {
            environment.actionOnRandomDoor(true);
        }
        else {
            environment.actionOnDoor(doorName, true);
        }
    }
    public void detectClosingDoor(String doorName) {
        if(doorName.equalsIgnoreCase("random")) {
            environment.actionOnRandomDoor(false);
        }
        else {
            environment.actionOnDoor(doorName, false);
        }
    }

    public void detectCameraPresence(String cameraName) {
        if(cameraName.equalsIgnoreCase("random")) {
            environment.randomCameraPresenceDetection();
        } else {
            environment.cameraPresenceDetection(cameraName);
        }
    }
}
