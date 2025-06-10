package events;

import commands.Command;
import commands.NullCommand;
import devices.Device;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event {  
    private final String type;
    private final Map<String, List<Command>> deviceActions = new HashMap<>(); 
    public Event(String type) { 
        this.type = type;
    }
    public List<Command> getCommands(Device dev) {
        List<Command> commandList = deviceActions.get(dev.getName());
        if (commandList == null) { 
            commandList = new ArrayList<>();
            commandList.add(NullCommand.getInstance());
        }   
        return Collections.unmodifiableList(commandList);
    }

    public void addCommand(Command cmd, String devName) {
        List<Command> commandList;
        if(deviceActions.containsKey(devName)) {
            commandList = deviceActions.get(devName);
            commandList.add(cmd);
        }
        else {
            commandList = new ArrayList<>();
            commandList.add(cmd);
            deviceActions.put(devName, commandList);
        }
    }
    // can be useful, we'll see if is needed
    @Override
    public String toString() {
        return "Event type: " + type + System.lineSeparator();
    }

    public String getType() {
        return type;
    }
} 
