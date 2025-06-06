package controller;

import commands.Command;
import devices.Device;
import devices.ObservableDevice;
import events.Event;
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
    private final List<Device> device_list = new ArrayList<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Map<ScheduledFuture<?>, Boolean> scheduledCommands = new HashMap<>();
    
    private SmartHomeController() {}

    public static SmartHomeController getInstance() {
        if(instance == null) { 
            instance = new SmartHomeController();
        }
        return instance;
    }

    public void addDevice(Device device) {
        device_list.add(device);
        System.out.println(device.getName() + " just got registered to the SmartHome Controller!");
    }

    public void removeDevice(Device device) {
        if(device_list.contains(device)) {
            if(device instanceof ObservableDevice d) {
                d.detach();
            }
            device_list.removeIf(dev -> (dev.getName().equals(device.getName())));
            System.out.println(device.getName() + " just got removed from the SmartHome Controller...");
        }
    }

    public void printDeviceList() {
        device_list.stream().forEach(dev -> System.out.println(dev.getName() + " - " + dev.getClass()));
    }

    public void triggerEvent(Event event) {}
    @Override
    public void update(Event event) {}

    public void scheduleCommand(String devName, long delaySecs, long repeatSecs, Command cmd) {
        if(device_list.stream().anyMatch((dev) -> (dev.getName().equals(devName)))) {
            ScheduledFuture<?> handle = null; 
            if(repeatSecs > 0) {
                handle = scheduler.scheduleAtFixedRate((Runnable) cmd, delaySecs, repeatSecs, TimeUnit.SECONDS);
            }
            else {
                handle = scheduler.schedule((Runnable) cmd, delaySecs, TimeUnit.SECONDS);
            }
            if (handle == null) { // manage errors in handle creation
                System.err.println("Error in scheduling of command. Try again");
                return;
            }
            scheduledCommands.put(handle, (repeatSecs > 0)); 
            // the boolean is used to track if the task repeats or not
        }
        else {
            System.out.println("Device " + devName + " is not monitored by the controller!");
        }
    }
}
