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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import scenario.Scenario;

public class UserFacade {
    
    private Environment simulation; // should we encapsulate the environment in the menu????
    // maybe it's better to have it composed inside the controller?
    // figure it out
    private SmartHomeController controller;
    private Scanner scan;
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

        scan = new Scanner(System.in);
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
    private void scheduleACommandAskCommand(String devName) {
        gui.printToWindow("what command do you want to schedule?");
        gui.setMenu((cmdName) -> {
            Command cmd = cmdFactory.createCommand(cmdName);
            if (cmd == null) {
                gui.printToWindow("The command is not recognized.");
                scheduleACommandAskCommand(devName);
                return;
            }
        scheduleACommandAskDelay(devName, cmd); 
        });
    }
    
    private void scheduleACommandAskDelay(String devName, Command cmd) {
        gui.printToWindow("how long until the command is executed? (in seconds)");
        gui.setMenu((delayInput) -> {
            long delaySecs;
            try {
                delaySecs = Long.parseLong(delayInput.trim()) + 1;
            } catch (NumberFormatException e) {
                gui.printToWindow("Invalid input, using default value of 1 second...");
                delaySecs = 1;
        }
        scheduleACommandAskRepeat(devName, cmd, delaySecs);
        });
    }

    private void scheduleACommandAskRepeat(String devName, Command cmd, long delaySecs) {
        gui.printToWindow("How often shall the command be executed? (in seconds)");
        gui.setMenu((repeatInput) -> {
            long repeatSecs;
            try {
                repeatSecs = Long.parseLong(repeatInput.trim());
            } catch (NumberFormatException e) {
                gui.printToWindow("Invalid input, using default value of 0 seconds...");
                repeatSecs = 0;
            }
            controller.scheduleCommand(devName, delaySecs, repeatSecs, cmd);
            gui.setMenu(this::scheduleACommandLoop);
            gui.printCommandScheduler(controller);
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
                cmdRegister.getAvailableCommands(controller.getDeviceFromName(devName).getBaseType()).stream().forEach(cmd -> gui.printToWindow("\t" + cmd));
                scheduleACommandAskCommand(devName); 
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
                break;
            case "2":
                gui.setMenu(this::createScenarioLoop);
                gui.printShowScenarios(userScenarios);
                break;
            case "3":
                scheduleScenarioLoop();
                break;
            case "4":
                triggerAScenarioLoop();
                break;
            case "5":
                removeScenarioLoop();
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
    
    private void removeScenarioLoop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeScenarioLoop'");
    }

    private void scheduleScenarioLoop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'scheduleScenarioLoop'");
    }

    private void createScenarioLoop(String scenarioName) {
        switch (scenarioName) {
            case "":
                gui.setMenu(this::mainLoop);
                gui.printMainMenu();
                break;
            default:
                if(userScenarios.stream().anyMatch(scenario -> scenario.getName().equals(scenarioName))) {
                    gui.printToWindow("A scenario with the same name already exists.");
                    break;
                }
                // include logic of adding scenario.
        }
    }

    private void editScenarioLoop() {
        gui.printEditScenario();
        //TODO
    }

    private void triggerAScenarioLoop() {
        
        gui.printTriggerScenario();
        String scenarioName;

        switch (scenarioName = scan.nextLine()) {
            case "":
                gui.setMenu(this::mainLoop);
                gui.printMainMenu();
                break;
            default:
                /*... */
                break;
        }
        triggerAScenarioLoop();
    }

}