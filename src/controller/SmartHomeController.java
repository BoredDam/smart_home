package controller;

import commands.Command;
import devices.Device;
import devices.ObservableDevice;
import devices.camera.Camera;
import devices.door.Door;
import devices.thermostat.Thermostat;
import events.Event;
import events.EventManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import scenario.Scenario;

public class SmartHomeController implements Observer {
    private record ScheduledCommand(String devName, String commandName, boolean repeats, ScheduledFuture<?> handle) {}
    private record ScheduledScenario(String scenarioName, Scenario scenario, ScheduledFuture<?> handle) {}

    private static SmartHomeController instance;
    private final EventManager eventManager;
    private final List<Device> device_list = new ArrayList<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final List<ScheduledCommand> scheduledCommands = new ArrayList<>();
    private final Map<ObservableDevice, Boolean> listenedDevices = new HashMap<>();
    private final List<ScheduledScenario> scheduledScenarios = new ArrayList<>();    

    private SmartHomeController() {
        eventManager = EventManager.getInstance();
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
        if(__isMonitored__(device)) {
            listenedDevices.put(device, false);
        } else {
            listenedDevices.put(device, true);
        }
        
    }

    public String printDevMonitoringState(Device dev) {
        if(dev instanceof ObservableDevice od){
            return (listenedDevices.get(od) ? "monitored" : "non-monitored");
        }
        return "non-monitorable";
    }

    private boolean __isMonitored__(Device dev) {
        if(dev instanceof ObservableDevice od){
            return (listenedDevices.get(od));
        }
        return false;
    }

    private void printMessage(String message) {
        System.out.println("[SmartHomeController] " + message);
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
        device_list.removeIf(dev -> (dev.getName().equals(device.getName())));
        printMessage(device.getName() + " just got removed from the SmartHome Controller...");
        return true;
        
    }

    
    /**
     * Prints out the device list of the <code>SmartHomeController</code>.
     */
    public void printDeviceList() {
        device_list.stream().forEach(dev -> System.out.println("| " + dev.getName() + "\t\t" + dev.getType() 
        + "\t" + (dev.isOn() ? "ON" : "OFF") + "\t" + printDevMonitoringState(dev)));
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
                    } else {
                        printMessage("[" + ts.getName() + "] Temperature measured: " + ts.getTemperature() + "°C");
                    }
                }
                case Camera _, Door _ -> {
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
        scheduledScenarios.add(new ScheduledScenario(scenarioName, scenario, handle));
        // don't think we should repeat scenarios...
    }

    public List<Device> returnViewOnlyDevices() {
        return Collections.unmodifiableList(device_list);
    }


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
}
