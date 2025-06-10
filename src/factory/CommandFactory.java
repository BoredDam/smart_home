package factory;

import commands.Command;
import commands.NullCommand;
import commands.airConditionerCommands.*;
import commands.cameraCommands.*;
import commands.doorCommands.*;
import commands.generalPurposeCommands.*;
import commands.lightCommands.*;
import commands.speaker.*;
import commands.thermostatCommands.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandFactory {
    private static CommandFactory instance;
    private final Map<String, Function<String[], Command>> commandMap = new HashMap<>(); 
    private CommandFactory() {
        initializeMap();
    }

    private void initializeMap() {
        // general purpose commands
        commandMap.put("TurnOnCommand", (_) -> new TurnOnCommand());
        commandMap.put("TurnOffCommand", (_) -> new TurnOffCommand());
        // lights commands
        commandMap.put("SwitchLightCommand", (_) -> new SwitchLightCommand());
        // thermostat commands
        commandMap.put("SetUpperBoundCommand", (args) -> new SetUpperBoundCommand(Float.parseFloat(args[0])));
        commandMap.put("SetLowerBoundCommand", (args) -> new SetLowerBoundCommand(Float.parseFloat(args[0]))); 
        // door commands
        commandMap.put("LockCommand", (_) -> new LockCommand());
        commandMap.put("UnlockCommand", (_) -> new UnlockCommand());
        // speaker commands
        commandMap.put("PlayCommand", (_) -> new PlayCommand());
        commandMap.put("PauseCommand", (_) -> new PauseCommand());
        commandMap.put("SetVolumeCommand", (args) -> new SetVolumeCommand(Integer.parseInt(args[0])));
        commandMap.put("StopCommand", (_) -> new StopCommand());
        // air conditioner commands
        commandMap.put("SetTargetTemperatureCommand", (args) -> new SetTargetTemperatureCommand(Float.parseFloat(args[0])));
        // camera commands
        commandMap.put("RecordVideo", (_) -> new RecordVideo());
        commandMap.put("CaptureImage", (_) -> new CaptureImage());
    }
    public static CommandFactory getInstance() {
        if(instance == null) 
            instance = new CommandFactory();
        return instance;
    }

    public Command createCommand(String type, String... args) {
        Function<String[], Command> function = commandMap.get(type);
        if(function == null)
            return (Command) NullCommand.getInstance(); // it is nice to have the null command, but it can be scheduled and just occupies a thread for nothing
        return function.apply(args);

    }

}
