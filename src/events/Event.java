package events;

import commands.Command;
import java.util.HashMap;
import java.util.Map;
public abstract class Event {  
    protected Command action;
    protected Map<Class<?>, Command> typeAction = new HashMap<>(); 
    public Event() { }

    public Command getCommand() { return action; }
} 
