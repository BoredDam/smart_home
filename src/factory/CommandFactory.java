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

/**
 * the CommandFactory class is based on the "Factory Method" design pattern by the GoF.
 * It offers a simple way to create instances of command objects.
 * It is also a Singleton.
 * 
 * @author Paolo Volpini
 * @author Damiano Trovato
 */
public class CommandFactory {
    private static CommandFactory instance;
    private record commandType(int argc, Function<String[], Command> cmdGen, String argDesc) {}
    private final Map<String, commandType> commandMap = new HashMap<>(); 
    private CommandFactory() {
        initializeMap();
    }

    /**
     * @hidden Method called by the constructor of the CommandFactory. For every device type of the system, 
     * it has to put a new record in the commandMap. 
     */

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

    /**
     * @param cmdType the name of a command.
     * @return the number of arguments required by the specified command.
     * @hidden this makes the code a lot more scalable :)
     */
    public int getArgumentCount(String cmdType) {
        return commandMap.get(cmdType.toLowerCase()) == null ? -1 : commandMap.get(cmdType.toLowerCase()).argc;
    }

    /**
     * @param cmdType the name of a command.
     * @return a string which describes the requirements of arguments of the specified command.
     */
    public String getArgumentDescription(String cmdType) {
        return commandMap.get(cmdType.toLowerCase()) == null ? null : commandMap.get(cmdType.toLowerCase()).argDesc;
    }

    /**
     * Creates an instance of a command.
     * @param cmdType the command type you want to create and return an instance of.
     * @param args the eventual arguments needed by the command.
     * @return an instance of the requested command.
     */
    public Command createCommand(String cmdType, String... args) {
        Function<String[], Command> function = commandMap.get(cmdType.toLowerCase()).cmdGen;

        if (function == null) {
            return null; 
        }
        
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
