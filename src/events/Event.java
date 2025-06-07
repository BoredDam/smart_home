package events;

import commands.Command;
import commands.NullCommand;
import devices.Device;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class Event {  
    private String type;
    protected Map<Class<? super Device>, Class<? super Command>> typeAction = new HashMap<>(); 
    public Event(String type) { 
        this.type = type;
    }
    public Command getCommand(Device dev) {
        try {
            Class<?> commandClass = typeAction.get(dev.getClass());
            if (commandClass == null) { 
                return NullCommand.getInstance();
            }   
            return (Command) commandClass.getConstructor(Device.class).newInstance(dev);
        }
        catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
            e.getCause().printStackTrace();
            return NullCommand.getInstance();
        }
    }

    // can be useful, we'll see if is needed
    @Override
    public String toString() {
        return "Event type: " + type;
    }
} 
