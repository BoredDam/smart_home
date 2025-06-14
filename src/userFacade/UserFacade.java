package userFacade;


import commands.Command;
import commands.CommandRegister;
import controller.SmartHomeController;
import debugTools.Environment;
import devices.Device;
import devices.ObservableDevice;
import factory.CommandFactory;
import factory.DecoratorFactory;
import factory.DeviceFactory;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import scenario.Scenario;

public class UserFacade {
    private class commandScheduleInfo {
        String devName;
        long delaySecs;
        long repeatSecs;
        Command cmd;
        Consumer<commandScheduleInfo> afterSetup;
        commandScheduleInfo(Consumer<commandScheduleInfo> afterSetup) {
            this.devName = null;
            this.delaySecs = 0;
            this.repeatSecs = 0;
            this.cmd = null;
            this.afterSetup = afterSetup;
        }   
    }
    
    private Environment simulation; // should we encapsulate the environment in the menu????
    // maybe it's better to have it composed inside the controller?
    // figure it out
    private SmartHomeController controller;
    private DeviceFactory devFactory;
    private DecoratorFactory decFactory;
    private CommandFactory cmdFactory;
    private CommandRegister cmdRegister;
    private final GUIPrinter gui;
    private final List<Scenario> userScenarios = new ArrayList<>();
    public UserFacade(GUIWindow guiWindow) {
        gui = new GUIPrinter(guiWindow);
    }

    private void initializeDefaultController() {

        gui.printToWindow("Setting default controller...");
        controller = SmartHomeController.getInstance();
        devFactory = DeviceFactory.getInstance();
        cmdRegister = CommandRegister.getInstance();
        cmdFactory = CommandFactory.getInstance();
        decFactory = DecoratorFactory.getInstance();
    }

    public void mainDialog() {
        // if we have more than one type of controller, we can further modify this. But this is not the case 
        // and we just leave the possibility to extend the software
        gui.printToWindow("Welcome! This is a Smart Home simulator. We will now setup the basic environment for your simulation...");
        initializeDefaultController();
        gui.printSeparator();
        gui.printToWindow("Everything should be in place!");
        gui.printSeparator();
        gui.setMenu(this::mainLoop);
        gui.printMainMenu();
    }

    private void mainLoop(String input) {
        //gui.printMainMenu();
        switch (input) {
            case "1":
                gui.setMenu(this::deviceConfigLoop);
                gui.printDeviceConfig();
                break;

            case "2":
                gui.setMenu(this::scheduleACommandLoop);
                gui.printCommandScheduler(controller);
                break;

            case "3":
                gui.setMenu(this::scenariosMenuLoop);
                gui.printScenariosMenu();
                break;

            case "4":
                //environmentSettings...
                break; 
        
            case "q":
                System.out.println("bye bye...");
                controller.shutdown();
                System.exit(0);
                break;

            default:
                break;
        }
    }

    private void deviceConfigLoop(String input) {

        gui.printDeviceConfig();
        switch (input) {
            case "1":
                showDevicesLoop();
                gui.setMenu((_) -> { gui.setMenu(this::deviceConfigLoop); gui.printDeviceConfig();});
                break;

            case "2":
                gui.setMenu(this::addDeviceLoop);
                gui.printAddDevice(controller);
                break;

            case "3":
                gui.setMenu(this::removeDeviceLoop);
                gui.printRemoveDevice(controller);
                break;

            case "4":
                gui.setMenu(this::addAFunctionalityLoop);
                gui.printAddAFunctionality(controller);
                break; 
        
            case "5":
                gui.setMenu(this::deviceMonitoringLoop);
                gui.printDeviceMonitoring(controller);
                break;

            case "":
                gui.setMenu(this::mainLoop);
                gui.printMainMenu();
                break;
                
            default:
                break;
        }
    }

    private void showDevicesLoop() {
        gui.printShowDevice(controller);
    }
    
    private void removeDeviceLoop(String devName) {
        
        switch(devName) {
            case "":
                gui.setMenu(this::deviceConfigLoop);
                gui.printDeviceConfig();
                break;
            default:
                controller.removeDevice(controller.getDeviceFromName(devName));
                gui.printRemoveDevice(controller);
                break;
        }
    }

    private void addDeviceLoop(String typeName) {
        switch(typeName) {
            case "":
                gui.setMenu(this::deviceConfigLoop);
                gui.printDeviceConfig();
                break;
            default:
                if(!devFactory.lookFor(typeName)) {
                    gui.printToWindow("This device type is not supported.");
                    break;
                }
                gui.printToWindow("Type your new device name.");
                gui.setMenu((input) -> {
                    String devName = input.trim(); 
                    controller.addDevice(devFactory.createDevice(typeName, devName)); 
                    gui.setMenu(this::addDeviceLoop);
                    gui.printAddDevice(controller);
                } );
                break;
        }
        
    }

    private void deviceMonitoringLoop(String devName) {
        switch (devName) {
            case "":
                gui.setMenu(this::deviceConfigLoop);
                gui.printDeviceConfig();
                break;
            default:
                if (controller.getDeviceFromName(devName) instanceof ObservableDevice od) {
                    controller.toggleDeviceMonitoring(od);
                }
                else {
                    gui.printToWindow("Device not monitorable.");
                }
                gui.printDeviceMonitoring(controller);
                break;
        }
    }

    // boolean toSchedule is used to determine if the command should be INSTANLTY scheduled or not
    // this is used only because the scenario needs also the records to schedule the command
    // and i don't want to re-write all the code just for the scenario
    private void scheduleACommandAskCommand(commandScheduleInfo info) {
        cmdRegister.getAvailableCommands(controller.getDeviceFromName(info.devName).getBaseType()).stream().forEach(cmd -> gui.printToWindow("\t" + cmd));
        gui.printToWindow("what command do you want to schedule?");
        gui.setMenu((cmdName) -> {
            Command cmd = cmdFactory.createCommand(cmdName);
            if (cmd == null) {
                gui.printToWindow("The command is not recognized.");
                scheduleACommandAskCommand(info);
                return;
            }
            info.cmd = cmd;
            scheduleACommandAskDelay(info); 
        });
    }
    
    private void scheduleACommandAskDelay(commandScheduleInfo info) {
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

    private void handleAskRepeat(commandScheduleInfo info, String repeatInput) {
        long repeatSecs;
            try {
                repeatSecs = Long.parseLong(repeatInput.trim());
            } catch (NumberFormatException e) {
                gui.printToWindow("Invalid input, using default value of 0 seconds...");
                repeatSecs = 0;
            }
            info.repeatSecs = repeatSecs;
    }
    
    private void scheduleACommandAskRepeat(commandScheduleInfo info) {
        gui.printToWindow("How often shall the command be executed? (in seconds)");
        gui.setMenu((repeatInput) -> {
            handleAskRepeat(info, repeatInput);
            info.afterSetup.accept(info);
        });
    }

    private void scheduleACommandLoop(String devName) {
        switch (devName) {
            case "":
                gui.setMenu(this::mainLoop);
                gui.printMainMenu();
                break;
            default:    
                gui.printToWindow("available commands for " + devName + ":");
                if(controller.getDeviceFromName(devName) == null) {
                    gui.printToWindow("This device does not exist.");
                    break;
                }
                commandScheduleInfo info = new commandScheduleInfo( 
                    (rec) -> { controller.scheduleCommand(rec.devName, rec.delaySecs, rec.repeatSecs, rec.cmd); 
                        gui.setMenu(this::scheduleACommandLoop);
                        gui.printCommandScheduler(controller);
                    }
                );
                info.devName = devName;
                scheduleACommandAskCommand(info); 
                break;
            }
    }
    
    private void addAFunctionalityLoop(String devName) {
        switch(devName) {
            case "":
                gui.setMenu(this::deviceConfigLoop);
                gui.printDeviceConfig();
                break;
            
            default:
                if(controller.getDeviceFromName(devName) == null) {
                    gui.printToWindow("Device does not exist.");
                    break;
                }
                gui.printToWindow("which function you want to add?");
                gui.setMenu( (decName) -> {
                    Device decoratedDev = decFactory.addFunctionality(controller.getDeviceFromName(devName), decName);
                    if (decoratedDev == null) {
                        gui.printToWindow("This functionality is not supported.");
                    } 
                    else {
                        controller.updateFunctionality(devName, decoratedDev);
                        gui.printAddAFunctionality(controller);
                    }
                    gui.setMenu(this::addAFunctionalityLoop);
                } );
                break;
        }

    }
    
    private void scenariosMenuLoop(String input) {
        switch (input) {
            case "1":
                showScenariosLoop();
                gui.setMenu((_) -> { gui.setMenu(this::scenariosMenuLoop); gui.printScenariosMenu();});
                break;
            case "2":
                gui.setMenu(this::createScenarioLoop);
                gui.printCreateScenario(userScenarios);
                break;
            case "3":
                gui.setMenu(this::editScenarioLoop);
                gui.printEditScenario(userScenarios);
                break;
            case "4":
                gui.setMenu(this::scheduleScenarioLoop);
                gui.printScenarioScheduler(userScenarios);
                break;
            case "5":
                gui.setMenu(this::triggerAScenarioLoop);
                gui.printTriggerScenario(userScenarios);
                break;
            case "6":
                gui.setMenu(this::removeScenarioLoop);
                gui.printRemoveScenario(userScenarios);
                break;  
            case "":
                gui.setMenu(this::mainLoop);
                gui.printMainMenu();
                break;       
            default:
                break;
        }
    }

    private void showScenariosLoop() {
        gui.printShowScenarios(userScenarios);
    }

    private Scenario getScenarioFromName(String scenarioName) {
        return userScenarios.stream().filter(s -> s.getName().equals(scenarioName)).findFirst().orElse(null);
    }

    private void removeScenarioLoop(String scenarioName) {
        switch(scenarioName) {
            case "":
                gui.setMenu(this::scenariosMenuLoop);
                gui.printScenariosMenu();
                break;
            default:
                Scenario selected = getScenarioFromName(scenarioName);
                if(selected == null) {
                    gui.printToWindow("A scenario with that name does not exist.");
                    break;
                }
                userScenarios.remove(selected);
                gui.printRemoveScenario(userScenarios);        
                break;
        }    
    }

    private long parseDelay(String time) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
        LocalTime date = LocalTime.parse(time, formatter);
        long elapsedTime = ChronoUnit.SECONDS.between(LocalTime.now(), date);
        return elapsedTime <= 0 ? 86400 + elapsedTime : elapsedTime;
    }
    
    private void scheduleAScenarioAskDelay(Scenario scenario) {
        gui.printToWindow("When do you want to schedule it? Specify time with format HH:mm (like 8:30, or 21:00)");
        gui.setMenu((time) -> {
            if(time.isEmpty()) {
                scheduleAScenarioAskDelay(scenario);
                return;
            }
            long seconds;
            try {
                seconds = parseDelay(time);   
            } catch (DateTimeParseException e) {
                System.out.println("Format provided is broken or not supported, using this current hour...");
                seconds = 86400;
            }
            controller.scheduleScenario(scenario.getName(), scenario, seconds);
            gui.setMenu(this::scenariosMenuLoop);
            gui.printScenariosMenu();
        });

    }
    
    private void scheduleScenarioLoop(String scenarioName) {
        switch(scenarioName) {
            case "":
                gui.setMenu(this::scenariosMenuLoop);
                gui.printScenariosMenu();
                break;
            default:
                Scenario selected = getScenarioFromName(scenarioName);
                if(selected == null) {
                    gui.printToWindow("A scenario with that name does not exist.");
                    break;
                }
                scheduleAScenarioAskDelay(selected);
        }
    }

    private boolean scenarioNameCollision(String scenarioName) {
        return userScenarios.stream().anyMatch(scenario -> (scenario.getName().equals(scenarioName)));
    }

    private void createScenarioLoop(String scenarioName) {
        switch (scenarioName) {
            case "":
                gui.setMenu(this::scenariosMenuLoop);
                gui.printScenariosMenu();
                break;
            default:
                if(scenarioNameCollision(scenarioName)) {
                    gui.printToWindow("A scenario with the same name already exists.");
                    break;
                }
                userScenarios.add(new Scenario(scenarioName));
                gui.printCreateScenario(userScenarios);
                break;
        }
    }

    private void setupScenarioNameHelper(Scenario scenario) {
        gui.printToWindow("Type the new name for the scenario (or nothing to cancel).");
        gui.setMenu((newName) -> {
            if(newName.isEmpty()) {
                gui.setMenu(this::editScenarioLoop);
                gui.printEditScenario(userScenarios);
                return;
            }
            if(scenarioNameCollision(newName)){
                gui.printToWindow("A scenario with that name already exists.");
                setupScenarioNameHelper(scenario);
                return;
            }
            scenario.changeName(newName);
            gui.setMenu(this::editScenarioLoop);
            gui.printEditScenario(userScenarios);
        });
    }
    
    private void addCommandToScenarioHelper(Scenario scenario) {
        gui.printToWindow(controller.deviceListToString());
        gui.printToWindow("Type the device name to which you want to add a command (or nothing to cancel).");
        gui.setMenu((devName) -> {
            if(devName.isEmpty()) {
                gui.setMenu(this::editScenarioLoop);
                gui.printEditScenario(userScenarios);
                return;
            }
            Device dev = controller.getDeviceFromName(devName);
            if(dev == null) {
                gui.printToWindow("This device does not exist.");
                return;
            }
            commandScheduleInfo info = new commandScheduleInfo(
                (rec) -> { scenario.addCommand(rec.devName, rec.delaySecs, rec.repeatSecs, rec.cmd);
                    gui.setMenu(this::editScenarioLoop);
                    gui.printEditScenario(userScenarios);
                }
            );
            info.devName = devName;
            scheduleACommandAskCommand(info);
        });
    }
    
    private void removeCommandFromScenarioHelper(Scenario scenario) {
        gui.printToWindow(scenario.commandListToString());
        gui.printToWindow("Type the index of the command you want to remove (or nothing to cancel).");
        gui.setMenu((input) -> {
            if(input.isEmpty()) {
                gui.setMenu(this::editScenarioLoop);
                gui.printEditScenario(userScenarios);
                return;
            }
            int index;
            try {
                index = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                gui.printToWindow("Invalid input, try again.");
                removeCommandFromScenarioHelper(scenario);
                return;
            }
            scenario.removeCommand(index);
            gui.setMenu(this::editScenarioLoop);
            gui.printEditScenario(userScenarios);
        });
    }
    
    private void deviceMonitoringHelper(Scenario scenario, boolean enable) {
        gui.printToWindow(controller.deviceListToString());
        gui.printToWindow("Type the monitorable device name on which you want set monitoring (or nothing to cancel).");
        gui.setMenu((devName) -> {
            if(devName.isEmpty()) {
                gui.setMenu(this::editScenarioLoop);
                gui.printEditScenario(userScenarios);
                return;
            }
            Device dev = controller.getDeviceFromName(devName);
            if(dev == null) {
                gui.printToWindow("This device does not exist.");
                return;
            }
            if(!(dev instanceof ObservableDevice)) {
                gui.printToWindow("This device is not monitorable.");
                return;
            }
            if(enable) {
                scenario.enableDeviceMonitoring((ObservableDevice) dev);
            } else {
                scenario.disableDeviceMonitoring((ObservableDevice) dev);
            }
            gui.setMenu(this::editScenarioLoop);
            gui.printEditScenario(userScenarios);
        });
    }

    private void internalScenarioEditLoop(Scenario scenario) {
        gui.printInternalScenarioEdit(scenario.getName());
        // menu must be defined like this, because we need the scenario object
        gui.setMenu((input) -> {
            switch(input) {
                case "1":
                    setupScenarioNameHelper(scenario);
                    break;
                case "2":
                    addCommandToScenarioHelper(scenario);
                    break;
                case "3":
                    removeCommandFromScenarioHelper(scenario);
                    break;
                case "4":
                    deviceMonitoringHelper(scenario, true);
                    break;
                case "5":
                    deviceMonitoringHelper(scenario, false);
                    break;
                case "":
                    gui.setMenu(this::scenariosMenuLoop);
                    gui.printScenariosMenu();
                    break;
            }
        });
    }

    private void editScenarioLoop(String scenarioName) {
        switch(scenarioName) {
            case "":
                gui.setMenu(this::scenariosMenuLoop);
                gui.printScenariosMenu();
                break;
            default:
                Scenario selected = getScenarioFromName(scenarioName);
                if(selected == null) {
                    gui.printToWindow("A scenario with that name does not exist.");
                    break;
                }
                internalScenarioEditLoop(selected);
                break;
        }
    }

    private void triggerAScenarioLoop(String scenarioName) {
        switch (scenarioName) {
            case "":
                gui.setMenu(this::scenariosMenuLoop);
                gui.printScenariosMenu();
                break;
            default:
                Scenario selected = getScenarioFromName(scenarioName);
                if(selected == null) {
                    gui.printToWindow("A scenario with that name does not exist.");
                    break;
                }
                selected.apply(controller);
                gui.printTriggerScenario(userScenarios);
                break;
        }
    }

}