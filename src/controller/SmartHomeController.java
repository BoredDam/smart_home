package controller;

import commands.Command;
import devices.Device;
import devices.ObservableDevice;
import devices.camera.Camera;
import devices.thermostat.Thermostat;
import events.Event;
import events.EventManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SmartHomeController implements Observer {
    
    private static SmartHomeController instance;
    private final EventManager eventManager;
    private final List<Device> device_list = new ArrayList<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Map<ScheduledFuture<?>, Boolean> scheduledCommands = new HashMap<>();
    private final Map<ObservableDevice, Boolean> listenedDevices = new HashMap<>();

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

    /**
     * Adds a device to the <code>SmartHomeController</code> device list.
     * @param device to add
     */
    public void addDevice(Device device) {
        if(device != null) {
            device_list.add(device);
            System.out.println(device.getName() + " just got registered to the SmartHomeController!");
            if(device instanceof ObservableDevice od) {
                listenedDevices.put(od, true);
                System.out.println(od.getName() + " is being monitored by controller!");

            }
        }
    }

    /**
     * Removes a specific device from the <code>SmartHomeController</code> 
     * device list, if it exists.
     * @param device to remove
     */
    public void removeDevice(Device device) {
        if(device != null && device_list.contains(device)) {
            if(device instanceof ObservableDevice d) {
                d.detach();
                listenedDevices.remove(d);
            }
            device_list.removeIf(dev -> (dev.getName().equals(device.getName())));
            System.out.println(device.getName() + " just got removed from the SmartHome Controller...");
        }
    }

    /**
     * Prints out the device list of the <code>SmartHomeController</code>.
     */
    public void printDeviceList() {
        device_list.stream().forEach(dev -> System.out.println(dev.getName() + " - " + dev.getClass().getSimpleName()));
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
        if(listenedDevices.get(dev)) {
            switch(dev) {
                case Thermostat ts -> {
                    if(ts.tooHot()) {
                        triggerEvent(eventManager.getEvent("HighTemperature"));
                    }
                    else if (ts.tooCold()) {
                        triggerEvent(eventManager.getEvent("LowTemperature"));
                    }
                    else {
                        System.out.println("Thermostat " + ts.getName() + " sent a notification, but temperature is OK.");
                    }
                }
                case Camera _ -> {
                    triggerEvent(eventManager.getEvent("Intrusion"));
                }
                default -> {
                    System.out.println("The device " + dev.getName() + " has sent a notification that is not currently supported by any event");
                }
            }
        }
    }


    public Device getDeviceFromName(String devName) {
        return (Device) device_list.stream().filter(d -> devName.equals(d.getName())).findFirst().orElse(null); 
    }

    public void scheduleCommand(String devName, long delaySecs, long repeatSecs, Command cmd) {
        Device dev = getDeviceFromName(devName);
        if(dev != null) {
            ScheduledFuture<?> handle = null; 
            if(repeatSecs > 0) {
                handle = scheduler.scheduleAtFixedRate( () -> { dev.performAction(cmd); }, delaySecs, repeatSecs, TimeUnit.SECONDS);
            }
            else {
                handle = scheduler.schedule( () -> { dev.performAction(cmd); }, delaySecs, TimeUnit.SECONDS);
            }
            if (handle == null) { // handle creation error
                System.err.println("Error in scheduling of command. Try again");
                return;
            }
        scheduledCommands.put(handle, (repeatSecs > 0)); 
            // the boolean is used to track if the task repeats or not
            // if a taks is to be repeated, we can get the handler and we can kill it
        }
        else {
            System.out.println("Device " + devName + " does not exist!");
        }
    }

    public void flushTasks() {
        // the function clears the map and deletes every scheduled command. Be advised because 
        // the function DOES NOT INCLUDE ANY DOUBLE CHECK: ONCE CALLED, EVERY HANDLER IS CLEARED 
        // AND THERE IS NO WAY TO RECOVER THE COMMANDS
        scheduledCommands.forEach( (handle, _) -> { handle.cancel(true); } );
        scheduledCommands.clear();
    }

    public void shutdown() {
        try (scheduler) {
            flushTasks();
        }
    }
}
