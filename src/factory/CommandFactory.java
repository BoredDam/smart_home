package factory;

import commands.Command;
import commands.airConditionerCommands.*;
import commands.cameraCommands.*;
import commands.doorCommands.*;
import commands.generalPurposeCommands.*;
import commands.lightCommands.*;
import commands.speakerCommands.*;
import commands.thermostatCommands.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandFactory {
    private static CommandFactory instance;
    private record commandType(int argc, Function<String[], Command> cmdGen, String argDesc) {}
    private final Map<String, commandType> commandMap = new HashMap<>(); 
    private CommandFactory() {
        initializeMap();
    }

    private void initializeMap() {
        // general purpose commands
        commandMap.put("turnon", new commandType(0, (_) -> new TurnOnCommand(), ""));
        commandMap.put("turnoff", new commandType(0, (_) -> new TurnOffCommand(), ""));
        // lights commands
        commandMap.put("switchlight", new commandType(0, (_) -> new SwitchLightCommand(), ""));
        // thermostat commands
        commandMap.put("setupperbound", new commandType(1, (args) -> new SetUpperBoundCommand(Float.parseFloat(args[0])), "<float under 50>"));
        commandMap.put("setlowerbound", new commandType(1, (args) -> new SetLowerBoundCommand(Float.parseFloat(args[0])), "<float over 10>"));
        // door commands
        commandMap.put("lock", new commandType(0, (_) -> new LockCommand(), ""));
        commandMap.put("unlock", new commandType(0, (_) -> new UnlockCommand(), ""));
        // speaker commands
        commandMap.put("play", new commandType(0, (_) -> new PlayCommand(), ""));
        commandMap.put("pause", new commandType(0, (_) -> new PauseCommand(), ""));
        commandMap.put("setvolume", new commandType(1, (args) -> new SetVolumeCommand(Integer.parseInt(args[0])), "<integer between 0 and 100>"));
        commandMap.put("stop", new commandType(0, (_) -> new StopCommand(), ""));
        // air conditioner commands
        commandMap.put("settargettemperature", new commandType(1, (args) -> new SetTargetTemperatureCommand(Float.parseFloat(args[0])), "<float between 10 and 50>"));
        // camera commands
        commandMap.put("recordvideo", new commandType(0, (_) -> new RecordVideo(), ""));
        commandMap.put("captureimage", new commandType(0, (_) -> new CaptureImage(), ""));
    }

    /**
     * Singleton pattern to ensure that there's only a single instance
     * of <code>CommandFactory</code>.
     * @return the only CommandFactory instance
     */
    public static CommandFactory getInstance() {
        if(instance == null) 
            instance = new CommandFactory();
        return instance;
    }

    public int getArgumentCount(String type) {
        return commandMap.get(type) == null ? -1 : commandMap.get(type).argc;
    }
    public String getArgumentDescription(String type) {
        return commandMap.get(type) == null ? null : commandMap.get(type).argDesc;
    }

    public Command createCommand(String type, String... args) {
        Function<String[], Command> function = commandMap.get(type).cmdGen;
        if(function == null)
            return null; 
        Command ret;
        try {
            ret = function.apply(args);
        } catch (Exception e) {
            System.out.println("The parameters provided for the command are invalid.");
            return null;
        }
        return ret;

    }

}
