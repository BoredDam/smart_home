package events;

import commands.Command;
import devices.Device;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Instances of the Event class describes a sequence of commands that are executed under certain conditions 
 * of the system. They are used to trigger the execution of commands on devices by the controller.
 * 
 * @author Paolo Volpini
 * @author Damiano Trovato
 */

public class Event {  
    private final String type; 
    private final Map<String, List<Command>> deviceActions = new HashMap<>(); // a map containing <device_name> <list-of-commands-to-execute>
    private final ArrayList<Command> emptyInstance = new ArrayList<>(); // used to return an empty list when no commands are present

    public Event(String type) { 
        this.type = type;
    }

    /**
     * @param dev a device.
     * @return a list of commands that are executed by the given device after this event.
     */
    public List<Command> getCommands(Device dev) {
        List<Command> commandList = deviceActions.get(dev.getName());
        if (commandList == null) { 
            return emptyInstance; 
        }   
        return Collections.unmodifiableList(commandList);
    }

    /**
     * Adds a command that has a device has to execute after this event.
     * @param cmd the command to execute.
     * @param devName the name of the device.
     */
    public void addCommand(Command cmd, String devName) {
        List<Command> commandList;

        if(deviceActions.containsKey(devName)) { // if the command-list of the device exists in the map...
            commandList = deviceActions.get(devName);  
            if(commandList.stream().noneMatch(command -> cmd.getClass().equals(command.getClass()))) { 
                commandList.add(cmd); // ...add the command
            }
        } else { // otherwise, create the list and add the command
            commandList = new ArrayList<>();
            commandList.add(cmd);
            deviceActions.put(devName, commandList);
        }
    }

    /**
     * @return the type of the event.
     */
    public String getType() {
        return type;
    }
} 
