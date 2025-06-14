package events;

/**
 * <code> EventManager </code> it's a class that encapsulates the logic behind creating events. 
 * Inside the class you can code the default events, using the <code>setUpDefaultEvents</code>.
 * It's also a Singleton.
 * 
 * @author Paolo Volpini
 * @author Damiano Trovato
 */


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
    private final Map<String, Event> eventManager = new HashMap<>();
    
    private EventManager() {}
    
    /**
     * Singleton pattern to ensure that there's only a single instance
     * of <code>EventManager</code>.
     * @return the only EventManager instance
     */
    public static EventManager getInstance() {
        if(instance == null) {
            System.out.println("[EventManager] Instance generated, events ready to be set...");
            instance = new EventManager();
            instance.eventManager.put("HighTemperature", new Event("HighTemperature"));
            instance.eventManager.put("LowTemperature", new Event("LowTemperature"));
            instance.eventManager.put("Intrusion", new Event("Intrusion"));
        }
        return instance;
    }

    /**
     * Gets an Event with the name specified in <code>type</code>.
     * @param type name of the Event 
     * @return an Event
     */
    public Event getEvent(String type) {
        return eventManager.get(type);
    }

    /**
     * Adds an Evemt to the <code>EventManager</code>
     * @param dev of the command you want to add
     * @param eventType
     * @param cmd 
     */ 
    public void addEventMonitoredDevice(Device dev, String eventType, Command cmd) {    
        Event e = eventManager.get(eventType);
        if(e == null) {
            System.out.println("Event not recognized in the system");
            return;
        }
        e.addCommand(cmd, dev.getName());
    }

    /**
     * This is a configuration for the default events, thus providing also a possibility for  
     * a future (probably inexistent...) update of the system that supports custom events
     * @param listDev the list from which import the devices to register events from.
     */ 
    public void setUpDefaultEvents(List<Device> listDev) {
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
                    // do nothing
                }
            }
        }
    }
}
