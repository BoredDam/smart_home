package events;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import commands.Command;
import devices.Device;

public class Event {

    // mappa 
    protected Map<Class<? extends Device>, Class<? extends Command>> typeAction = new HashMap<>(); 

    public Event() { 
    }
    public Command getCommand(Device dev) {
        try {
            Class<?> commandClass = typeAction.get(dev.getClass());
            if (commandClass == null) {
                System.err.println("No command class mapped for: " + dev.getClass().getSimpleName());
                return null;
            }   
            return (Command) commandClass.getConstructor(Device.class).newInstance(dev);
        }
        
        catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
            e.getCause().printStackTrace();
            return null;
        }
    }
}
