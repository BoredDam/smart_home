package userFacade;

import commands.Command;
import commands.CommandRegister;
import controller.SmartHomeController;
import devices.Device;
import devices.ObservableDevice;
import factory.CommandFactory;
import factory.DecoratorFactory;
import factory.DeviceFactory;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.function.Consumer;

/**
 * UserFacade is a class based on the Facade Design Patterns.
 * It gathers many different objects related to the SmartHouse-simulation 
 * to offer a simple facade for the client code.
 *  
 * @author Paolo Volpini
 * @author Damiano Trovato
 */
public class UserFacade {
    private class CommandScheduleInfo {
        String devName;
        long delaySecs;
        long repeatSecs;
        Command cmd;
        Consumer<CommandScheduleInfo> afterSetup;
        CommandScheduleInfo(Consumer<CommandScheduleInfo> afterSetup) {
            this.devName = null;
            this.delaySecs = 0;
            this.repeatSecs = 0;
            this.cmd = null;
            this.afterSetup = afterSetup;
        }   
    }

    private SmartHomeController controller;
    private DeviceFactory devFactory;
    private DecoratorFactory decFactory;
    private CommandFactory cmdFactory;
    private CommandRegister cmdRegister;
    private final GUIPrinter gui;

    public UserFacade(GUIWindow guiWindow) {
        gui = new GUIPrinter(guiWindow);
    }

    public void initializeUserSpace() {

        gui.printToWindow("Setting up user space...");
        controller = SmartHomeController.getInstance();
        devFactory = DeviceFactory.getInstance();
        cmdRegister = CommandRegister.getInstance();
        cmdFactory = CommandFactory.getInstance();
        decFactory = DecoratorFactory.getInstance();
    }
 // < - Utility methods - >
    private void switchToMainLoop() {
        gui.setMenu(this::mainLoop);
        gui.printMainMenu();
    }

    private void switchToDeviceConfigLoop() {
        gui.setMenu(this::deviceConfigLoop);
        gui.printDeviceConfig();
    }

    private void switchToScheduleCommandLoop() {
        gui.setMenu(this::scheduleACommandLoop);
        gui.printCommandScheduler(controller.deviceListToString(""));
    }

    private void switchToScenariosMenuLoop() {
        gui.setMenu(this::scenariosMenuLoop);
        gui.printScenariosMenu();
    }

    private void switchToEnvironmentLoop() {
        gui.setMenu(this::environmentLoop);
        gui.printEnvironmentSettings();
    }

    private void switchToAddDeviceLoop() {
        gui.setMenu(this::addDeviceLoop);
        gui.printAddDevice(controller.deviceListToString(""), devFactory.availableTypesToString());
    }

    private void switchToEditScenarioLoop() {
        gui.setMenu(this::editScenarioLoop);
        gui.printEditScenario(controller.scenariosListToString());
    }

    // !< - End of utility methods - >


    public void mainDialog() {
        // if we have more than one type of controller, we can further modify this. But this is not the case 
        // and we just leave the possibility to extend the software
        gui.printToWindow("Welcome! This is a Smart Home simulator.\nWe will now setup the basic environment for your simulation...");
        initializeUserSpace();
        gui.printSeparator();
        gui.printToWindow("Everything should be in place! Press any command to continue...");
        gui.printSeparator();
        gui.setMenu((_) -> {switchToMainLoop();});
    }

    private void mainLoop(String input) {
        switch (input) {
            case "1" -> switchToDeviceConfigLoop();

            case "2" -> switchToScheduleCommandLoop();

            case "3" -> {
                gui.setMenu(this::killCommandLoop);
                gui.printKillCommand(controller.scheduledCommandsToString());
            }
            case "4" -> switchToScenariosMenuLoop();
            
            case "5" -> switchToEnvironmentLoop();
            
            case "q" -> {
                System.out.println("bye bye...");
                controller.shutdown();
                System.exit(0);
            }
            
            default -> {}
        }
    }

    private void deviceConfigLoop(String input) {

        gui.printDeviceConfig();
        switch (input) {
            case "1" -> {
                gui.printShowDevice(controller.deviceListToString(""));
                gui.setMenu((_) -> { gui.setMenu(this::deviceConfigLoop); gui.printDeviceConfig();});
            }

            case "2" -> switchToAddDeviceLoop();

            case "3" -> {
                gui.setMenu(this::removeDeviceLoop);
                gui.printRemoveDevice(controller.deviceListToString(""));
            }

            case "4" -> {
                gui.setMenu(this::addAFunctionalityLoop);
                gui.printAddAFunctionality(controller.deviceListToString(""), decFactory.availableDecsToString(" - "));
            } 
        
            case "5" -> {
                gui.setMenu(this::deviceMonitoringLoop);
                gui.printDeviceMonitoring(controller.deviceListToString(""));
            }

            default -> {
                controller.setupDefaultEvents();
                switchToMainLoop();
            }
        }
    }

    
    private void removeDeviceLoop(String devName) {
        if(devName.isEmpty()) {
            switchToDeviceConfigLoop();
            return;
        }
        devName = devName.trim();
        controller.removeDevice(controller.getDeviceFromName(devName));
        gui.printRemoveDevice(controller.deviceListToString(""));
    }

    private void addDeviceLoop(String typeName) {
        if(typeName.isEmpty()) {
            switchToDeviceConfigLoop();
            return;
        }
        if(!devFactory.lookFor(typeName)) {
            gui.printToWindow("This device type is not supported.");
            return;
        }
        gui.printToWindow("Type your new device name.");
        gui.setMenu((input) -> {
            if(input.isEmpty()) {
                gui.printToWindow("Device name cannot be empty.");
                return;
            }
            String devName = input.trim(); 
            controller.addDevice(devFactory.createDevice(typeName.trim(), devName)); 
            switchToAddDeviceLoop();
        } );
        }

    private void deviceMonitoringLoop(String devName) {
        if(devName.isEmpty()) {
            switchToDeviceConfigLoop();
            return;
        }
        if (controller.getDeviceFromName(devName) instanceof ObservableDevice od) {
            controller.toggleDeviceMonitoring(od);
        }
        else {
            gui.printToWindow("Device not monitorable.");
        }
        gui.printDeviceMonitoring(controller.deviceListToString(""));
    }

    private void scheduleACommandAskCommand(CommandScheduleInfo info) {
        cmdRegister.getAvailableCommands(controller.getDeviceFromName(info.devName).getBaseType()).stream().forEach(cmd -> gui.printToWindow("\t" + cmd));
        gui.printToWindow("what command do you want to schedule?");
        gui.setMenu((cmdName) -> {
            int argc = cmdFactory.getArgumentCount(cmdName);
            // since now we have requested the argc, we know if the command exists in the factory
            if(argc < 0) {
                gui.printToWindow("Invalid command.");
                scheduleACommandAskCommand(info);
                return;
            }
            if(argc > 0)
                scheduleACommandCreateCommand(info, cmdName, argc);
            else {
                // instantly create the command
                info.cmd = cmdFactory.createCommand(cmdName);
                scheduleACommandAskDelay(info);
            }
        });
    }
    
    private void scheduleACommandCreateCommand(CommandScheduleInfo info, String cmdName, int argc) {
        gui.printToWindow("Command " + cmdName + " requires " + argc + " arguments: " + cmdFactory.getArgumentDescription(cmdName));
        gui.setMenu((args) -> {
            Command cmd = cmdFactory.createCommand(cmdName, args);
            if(cmd == null) {
                gui.printToWindow("Command creation failed, try again.");
                scheduleACommandCreateCommand(info, cmdName, argc);
                return;
            }
            info.cmd = cmd;
            scheduleACommandAskDelay(info);
        });

    }
    private void scheduleACommandAskDelay(CommandScheduleInfo info) {
        gui.printToWindow("how long until the command is executed? (in seconds)");
        gui.setMenu((delayInput) -> {
            long delaySecs;
            try {
                delaySecs = Long.parseLong(delayInput.trim()) + 1;
            } catch (NumberFormatException e) {
                gui.printToWindow("Invalid input, using default value of 1 second...");
                delaySecs = 1;
            }
            info.delaySecs = delaySecs;
            scheduleACommandAskRepeat(info);
        });
    }

    private void handleAskRepeat(CommandScheduleInfo info, String repeatInput) {
        long repeatSecs;
            try {
                repeatSecs = Long.parseLong(repeatInput.trim());
            } catch (NumberFormatException e) {
                gui.printToWindow("Invalid input, using default value of 0 seconds...");
                repeatSecs = 0;
            }
            info.repeatSecs = repeatSecs;
    }
    
    private void scheduleACommandAskRepeat(CommandScheduleInfo info) {
        gui.printToWindow("How often shall the command be executed? (in seconds)");
        gui.setMenu((repeatInput) -> {
            handleAskRepeat(info, repeatInput);
            info.afterSetup.accept(info);
        });
    }

    
    private void scheduleACommandLoop(String devName) {
        if(devName.isEmpty()) {
            switchToMainLoop();
            return;
        }  
        gui.printToWindow("available commands for " + devName + ":");
        if(controller.getDeviceFromName(devName) == null) {
            gui.printToWindow("This device does not exist.");
            return;
        }
        CommandScheduleInfo info = new CommandScheduleInfo( 
            (rec) -> { 
                controller.scheduleCommand(rec.devName, rec.delaySecs, rec.repeatSecs, rec.cmd); 
                switchToScheduleCommandLoop();
            } );
        info.devName = devName;
        scheduleACommandAskCommand(info); 
    }

    private void killCommandLoop(String inputIndex) {
        if(!inputIndex.isEmpty()) {
            int index;
            try {
                index = Integer.parseInt(inputIndex.trim());
                controller.killCommand(index);
            } catch (NumberFormatException e) {
                gui.printToWindow("Invalid input, please try again.");
            }
        }
        switchToMainLoop();
    }

    private void addFunctionHelper(String devName) {
        gui.printToWindow("which function you want to add?");
        gui.setMenu( (decName) -> {
            Device decoratedDev = decFactory.addFunctionality(controller.getDeviceFromName(devName), decName);
            if (decoratedDev == null) {
                gui.printToWindow("This functionality is not supported. Select another device.");
            } 
            else {
                controller.updateFunctionality(devName, decoratedDev);
                gui.printAddAFunctionality(controller.deviceListToString(""), decFactory.availableDecsToString(" - "));
            }
            gui.setMenu(this::addAFunctionalityLoop);
        });
    }

    private void addAFunctionalityLoop(String devName) {
        if(devName.isEmpty()) {
            switchToDeviceConfigLoop();
            return;
        }
        devName = devName.trim();
        if(controller.getDeviceFromName(devName) == null) {
            gui.printToWindow("Device does not exist.");
            return;
        }
        addFunctionHelper(devName);
    }
    
    private void scenariosMenuLoop(String input) {
        switch (input) {
            case "1" -> {
                gui.printShowScenarios(controller.scenariosListToString());
                gui.setMenu((_) -> { switchToScenariosMenuLoop();});
            }
            case "2" -> {
                gui.setMenu(this::createScenarioLoop);
                gui.printCreateScenario(controller.scenariosListToString());
            }
            case "3" -> switchToEditScenarioLoop();
            case "4" -> {
                gui.setMenu(this::scheduleScenarioLoop);
                gui.printScenarioScheduler(controller.scenariosListToString());
            }
            case "5" -> {
                gui.setMenu(this::triggerAScenarioLoop);
                gui.printTriggerScenario(controller.scenariosListToString());
            }
            case "6" -> {
                gui.setMenu(this::removeScenarioLoop);
                gui.printRemoveScenario(controller.scenariosListToString());
            }
            case "7" -> {
                gui.printScheduledScenarios(controller.scheduledScenariosToString());
                gui.setMenu((_) -> { switchToScenariosMenuLoop(); });
            }
            case "8" -> {
                gui.setMenu(this::killScenarioLoop);
                gui.printKillScheduledScenario(controller.scheduledScenariosToString());
            }
            default -> switchToMainLoop();       
        }
    }

    private void removeScenarioLoop(String scenarioName) {
        if(scenarioName.isEmpty()) {
            switchToScenariosMenuLoop();
            return;
        }
        scenarioName = scenarioName.trim();
        if(!controller.scenarioNameCollision(scenarioName)) {
            gui.printToWindow("A scenario with that name does not exist.");
            return;
        }
        controller.removeScenario(scenarioName);
        gui.printRemoveScenario(controller.scenariosListToString());            
    }

    private long parseDelay(String time) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
        LocalTime date = LocalTime.parse(time, formatter);
        long elapsedTime = ChronoUnit.SECONDS.between(LocalTime.now(), date);
        return elapsedTime <= 0 ? 86400 + elapsedTime : elapsedTime;
    }
    
    private void scheduleAScenarioAskDelay(String scenarioName) {
        gui.printToWindow("When do you want to schedule it? Specify time with format HH:mm (like 8:30, or 21:00)");
        gui.setMenu((time) -> {
            if(time.isEmpty()) {
                scheduleAScenarioAskDelay(scenarioName);
                return;
            }
            long seconds;
            try {
                seconds = parseDelay(time);   
            } catch (DateTimeParseException e) {
                System.out.println("Format provided is broken or not supported, using this current hour...");
                seconds = 86400;
            }
            controller.scheduleScenario(scenarioName, seconds);
            switchToScenariosMenuLoop();
        });

    }
    private void scheduleScenarioLoop(String scenarioName) {
        if(scenarioName.isEmpty()) {
            switchToScenariosMenuLoop();
            return;
        }
        if(!controller.scenarioNameCollision(scenarioName)) {
            gui.printToWindow("A scenario with that name does not exist.");
            return;   
        }
        if(controller.isScenarioScheduled(scenarioName)) {
            gui.printToWindow("This scenario is already scheduled. Remove its scheduling first.");
            return;
        }
        scheduleAScenarioAskDelay(scenarioName);
    }

    private void createScenarioLoop(String scenarioName) {
        if(scenarioName.isEmpty()) {
            switchToScenariosMenuLoop();
            return;
        }
        scenarioName = scenarioName.trim();
        if(controller.scenarioNameCollision(scenarioName)) {
            gui.printToWindow("A scenario with the same name already exists.");
            return;
        }
        controller.addScenario(scenarioName);
        gui.printCreateScenario(controller.scenariosListToString());
    }

    private void setupScenarioNameHelper(String scenarioName) {
        gui.printToWindow("Type the new name for the scenario (or nothing to cancel).");
        gui.setMenu((newName) -> {
            if(newName.isEmpty()) {
                switchToEditScenarioLoop();
                return;
            }
            if(controller.scenarioNameCollision(newName)){
                gui.printToWindow("A scenario with that name already exists.");
                setupScenarioNameHelper(scenarioName);
                return;
            }
            controller.changeScenarioName(scenarioName, newName);
            switchToEditScenarioLoop();
        });
    }
    
    private void addCommandToScenarioHelper(String scenarioName) {
        gui.printToWindow(controller.deviceListToString(""));
        gui.printToWindow("Type the device name to which you want to add a command (or nothing to cancel).");
        gui.setMenu((devName) -> {
            if(devName.isEmpty()) {
                switchToEditScenarioLoop();
                return;
            }
            Device dev = controller.getDeviceFromName(devName);
            if(dev == null) {
                gui.printToWindow("This device does not exist.");
                return;
            }
            CommandScheduleInfo info = new CommandScheduleInfo(
                (rec) -> { controller.addCommandToScenario(rec.devName, rec.delaySecs, rec.repeatSecs, rec.cmd, scenarioName);
                    switchToEditScenarioLoop();
                }
            );
            info.devName = devName;
            scheduleACommandAskCommand(info);
        });
    }
    
    private void removeCommandFromScenarioHelper(String scenarioName) {
        gui.printToWindow(controller.scenarioCommandsToString(scenarioName));
        gui.printToWindow("Type the index of the command you want to remove (or nothing to cancel).");
        gui.setMenu((input) -> {
            if(!input.isEmpty()) {
                 int index;
                try {
                    index = Integer.parseInt(input.trim());
                } catch (NumberFormatException e) {
                    gui.printToWindow("Invalid input, try again.");
                    removeCommandFromScenarioHelper(scenarioName);
                    return;
                }
                controller.removeCommandToScenario(scenarioName, index);
            }
            switchToEditScenarioLoop();
        });
    }

    private void killScenarioLoop(String scenarioName) {
        if(scenarioName.isEmpty()) {
            switchToScenariosMenuLoop();
            return;
        }
        scenarioName = scenarioName.trim();
        if(!controller.scenarioNameCollision(scenarioName)) {
            gui.printToWindow("A scenario with that name does not exist.");
            return;
        }
        controller.killScenario(scenarioName);
        gui.printKillScheduledScenario(controller.scheduledScenariosToString());
    }
    
    private void deviceMonitoringHelper(String scenarioName, boolean enable) {
        gui.printToWindow(controller.deviceListToString(""));
        gui.printToWindow("Type the monitorable device name on which you want set monitoring (or nothing to cancel).");
        gui.setMenu((devName) -> {
            if(!devName.isEmpty()) {
                Device dev = controller.getDeviceFromName(devName);
                if(dev == null) {
                    gui.printToWindow("This device does not exist.");
                    return;
                }
                if(!(dev instanceof ObservableDevice)) {
                    gui.printToWindow("This device is not monitorable.");
                    return;
                }
                controller.setScenarioDeviceMonitoring((ObservableDevice) dev, enable, scenarioName);
            }
            switchToEditScenarioLoop();
        });
    }

    private void internalScenarioEditLoop(String scenarioName) {
        gui.printInternalScenarioEdit(scenarioName);
        // menu must be defined like this, because we need the scenario object
        gui.setMenu((input) -> {
            switch(input) {
                case "1" -> setupScenarioNameHelper(scenarioName);
                case "2" -> addCommandToScenarioHelper(scenarioName);
                case "3" -> removeCommandFromScenarioHelper(scenarioName);
                case "4" -> deviceMonitoringHelper(scenarioName, true);
                case "5" -> deviceMonitoringHelper(scenarioName, false);
                default -> switchToScenariosMenuLoop();
            }
        });
    }

    private void editScenarioLoop(String scenarioName) {
        if(scenarioName.isEmpty()) {
            switchToScenariosMenuLoop();
            return;
        }
        if(!controller.scenarioNameCollision(scenarioName)) {
            gui.printToWindow("A scenario with that name does not exist.");
            return;
        }
        internalScenarioEditLoop(scenarioName);
    }

    private void triggerAScenarioLoop(String scenarioName) {
        if(scenarioName.isEmpty()) {
            switchToScenariosMenuLoop();
            return;
        }
        if(!controller.scenarioNameCollision(scenarioName)) {
            gui.printToWindow("A scenario with that name does not exist.");
            return;
        }
        controller.applyScenario(scenarioName);
        gui.printTriggerScenario(controller.scenariosListToString());
    }

    private void environmentLoop(String input) {
        switch(input) {
            case "1" -> controller.measureTemperatures();
            case "2" -> {
                gui.setMenu(this::openDoorLoop);
                gui.printOpenDoor(controller.deviceListToString("Door"));
            }
            case "3" -> {
                gui.setMenu(this::closeDoorLoop);
                gui.printCloseDoor(controller.deviceListToString("Door"));
            }
            case "4" -> {
                gui.setMenu(this::cameraPresenceLoop);
                gui.printCameraPresenceDetection(controller.deviceListToString("Camera"));
            }
            default -> switchToMainLoop();
        }
    }

    private void openDoorLoop(String doorName) {
        if(!doorName.isEmpty()) {
            controller.detectOpeningDoor(doorName);
        }
        switchToEnvironmentLoop();
    }

    private void closeDoorLoop(String doorName) {
        if(!doorName.isEmpty()) {
            controller.detectClosingDoor(doorName);
        }
        switchToEnvironmentLoop();
    }

    private void cameraPresenceLoop(String cameraName) {
        if(!cameraName.isEmpty()) {
            controller.detectCameraPresence(cameraName);
        }
        switchToEnvironmentLoop();
    }
}