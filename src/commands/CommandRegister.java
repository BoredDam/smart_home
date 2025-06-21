package commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CommandRegister class is used to list available commands for each device.
 * It's a Singleton. 
 * @author Paolo Volpini
 * @author Damiano Trovato
 * 
 */

public class CommandRegister {
    private final Map<String, List<String>> deviceCommands = new HashMap<>();
    private static CommandRegister instance;
    private static final List<String> emptyList = new ArrayList<>(); // used to return an empty list

    private CommandRegister() {
        deviceCommands.put("Light", new ArrayList<>(List.of("SwitchLight")));
        deviceCommands.put("Thermostat", new ArrayList<>(List.of("SetUpperBound", "SetLowerBound")));
        deviceCommands.put("Speaker", new ArrayList<>(List.of("Play", "Pause", "SetVolume", "Stop")));
        deviceCommands.put("Door", new ArrayList<>(List.of("Lock", "Unlock")));
        deviceCommands.put("Camera", new ArrayList<>(List.of("RecordVideo", "CaptureImage")));
        deviceCommands.put("AirConditioner", new ArrayList<>(List.of("SetTargetTemperature")));
        deviceCommands.put("OldHeater", new ArrayList<>());
        deviceCommands.forEach((_, listOfCommands) -> {listOfCommands.add("TurnOn"); listOfCommands.add("TurnOff"); } );
    }
    
    /**
     * Singleton pattern to ensure that there's only a single instance
     * of <code>CommandRegister</code>.
     * @return the only CommandRegister instance
     */
    public static CommandRegister getInstance() {
        if (instance == null) {
            instance = new CommandRegister();
        }
        return instance;
    }

    /**
     * @param deviceType a String that is evaluated to a device type.
     * @return a List with every available command with that device.
     */
    public List<String> getAvailableCommands(String deviceType) {
        return Collections.unmodifiableList(deviceCommands.get(deviceType) == null ? emptyList : deviceCommands.get(deviceType));
    }
    
}
