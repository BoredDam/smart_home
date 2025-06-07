package events;

import commands.*;
import commands.cameraCommands.CaptureImage;
import commands.cameraCommands.RecordVideo;
import commands.generalPurposeCommands.TurnOffCommand;
import commands.generalPurposeCommands.TurnOnCommand;
import devices.*;
import devices.adapter.OldHeaterAdapter;
import devices.airConditioner.AirConditioner;
import devices.camera.Camera;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EventManager {
    private static EventManager instance;
    private static final Map<String, Event> eventManager = new HashMap<>();
    
    private EventManager() {}
    
    public static EventManager getInstance() {
        if(instance == null)
            instance = new EventManager();
        return instance;
    }

    public Event getEvent(String type) {
        return eventManager.get(type);
    }

    // adds a command to the list of commands for the device
    public void addEventMonitoredDevice(Device dev, String eventType, Command cmd) {    
        Event e = eventManager.get(eventType);
        if(e == null) {
            System.out.println("Event not recognized in the system");
            return;
        }
        e.addCommand(cmd, dev.getName());
    }

    // this is a configuration for the default events
    // thus providing also a possibility for a future (probably inexistent...)
    // update of the system that supports custom events
    public void setUpDefaultEvents(List<Device> listDev) {
        eventManager.put("HighTemperature", new Event("HighTemperature"));
        eventManager.put("Intrusion", new Event("Intrusion"));
        Iterator<Device> it = listDev.iterator();
        while(it.hasNext()) {
            Device dev = it.next();
            switch(dev) {
                case AirConditioner _ -> {
                    addEventMonitoredDevice(dev, "HighTemperature", new TurnOnCommand());
                    addEventMonitoredDevice(dev, "LowTemperature", new TurnOnCommand());
                }
                case Camera _ -> {
                    addEventMonitoredDevice(dev, "Intrusion", new RecordVideo());
                    // let's consider something more complex... two commands instead of one
                    addEventMonitoredDevice(dev, "Intrusion", new CaptureImage());
                }
                case OldHeaterAdapter _ -> {
                    addEventMonitoredDevice(dev, "HighTemperature", new TurnOffCommand());
                    addEventMonitoredDevice(dev, "LowTemperature", new TurnOnCommand());
                }
                default -> {
                    System.err.println("Device not recognized");
                }
            }
        }
    }
}
