package events;

import commands.Command;
import devices.Device;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Instances of the Event class describe commands that are executed under certain conditions 
 * of the system.
 * 
 * @author Paolo Volpini
 * @author Damiano Trovato
 */

public class Event {  
    private final String type;
    private final Map<String, List<Command>> deviceActions = new HashMap<>();
    private final ArrayList<Command> emptyInstance = new ArrayList<>(); // used to return an empty list when no commands are present
    public Event(String type) { 
        this.type = type;
    }

    /**
     * @param dev a device.
     * @return a list of 
     */
    public List<Command> getCommands(Device dev) {
        List<Command> commandList = deviceActions.get(dev.getName());
        if (commandList == null) { 
            return emptyInstance; 
        }   
        return Collections.unmodifiableList(commandList);
    }

    /**
     * 
     * @param cmd
     * @param devName
     */
    public void addCommand(Command cmd, String devName) {
        List<Command> commandList;
        if(deviceActions.containsKey(devName)) {
            commandList = deviceActions.get(devName);
            if(commandList.stream().noneMatch(command -> cmd.getClass().equals(command.getClass()))) {
                commandList.add(cmd);
            }
        } else {
            commandList = new ArrayList<>();
            commandList.add(cmd);
            deviceActions.put(devName, commandList);
        }
    }

    
    public String getType() {
        return type;
    }
} 
