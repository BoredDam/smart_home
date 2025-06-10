package commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// CommandRegister is used to list available commands for each device  

public class CommandRegister {
    private final Map<String, List<String>> deviceCommands = new HashMap<>();
    private static CommandRegister instance;
    private CommandRegister() {
        deviceCommands.put("Light", new ArrayList<>(List.of("SwitchLightCommand")));
        deviceCommands.put("Thermostat", new ArrayList<>(List.of("SetUpperBoundCommand", "SetLowerBoundCommand")));
        deviceCommands.put("Speaker", new ArrayList<>(List.of("PlayCommand", "PauseCommand", "SetVolumeCommand", "SpeakerCommand", "StopCommand")));
        deviceCommands.put("Door", new ArrayList<>(List.of("LockCommand", "UnlockCommand")));
        deviceCommands.put("Camera", new ArrayList<>(List.of("RecordVideo", "CaptureImage")));
        deviceCommands.put("AirConditioner", new ArrayList<>(List.of("SetTargetTemperatureCommand")));
        deviceCommands.forEach((_, listOfCommands) -> {listOfCommands.add("TurnOnCommand"); listOfCommands.add("TurnOffCommand"); } );
    }
    
    public static CommandRegister getInstance() {
        if(instance == null)
            instance = new CommandRegister();
        return instance;
    }

    public List<String> getAvailableCommands(String deviceType) {
        return Collections.unmodifiableList(deviceCommands.get(deviceType) == null ? new ArrayList<>() : deviceCommands.get(deviceType));
    }
    
}
