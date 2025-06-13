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
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import scenario.Scenario;

public class UserFacade {
    
    private Environment simulation;
    private SmartHomeController controller;
    private Scanner scan;
    private DeviceFactory devFactory;
    private DecoratorFactory decFactory;
    private CommandFactory cmdFactory;
    private CommandRegister cmdRegister;
    private GUIprinter gui;

    private final List<Scenario> userScenario = new ArrayList<>();

    public UserFacade() {
        gui = new GUIprinter();
    }

    private void initializeDefaultController() {

        System.out.println("Setting default controller...");
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
        System.out.println("Welcome! This is a Smart Home simulator. We will now setup the basic environment for your simulation...");
        initializeDefaultController();
        gui.printSeparator();
        System.out.println("Everything should be in place!");
        gui.printSeparator();
        mainLoop();
    }

    private void deviceConfigLoop() {

        gui.printDeviceConfig();
        switch (scan.nextLine()) {
            case "1":
                showDevicesLoop();
                break;

            case "2":
                addDeviceLoop();
                break;

            case "3":
                removeDeviceLoop();
                break;

            case "4":
                addAFunctionalityLoop();
                break; 
        
            case "5":
                deviceMonitoringLoop();
                break;
            
            case "6":
                scenariosMenuLoop();
                break;

            case "":
                mainLoop();
                break;
                
            default:
                break;
        }
        deviceConfigLoop();
        /*
            // keep in mind that:
            // - there must be NO scenarios that share the same name;
            // after a keyword (like ready, or endSetup, or something else), ask what scenarios
            // should be applied, apply it and then call controller.setupDefaultEvents() and return.
        }
        */
    }

    private void mainLoop() {

        gui.printMainMenu();
        switch (scan.nextLine()) {
            case "1":
                deviceConfigLoop();
                break;

            case "2":
                scheduleACommandLoop();
                break;

            case "3":
                triggerAScenarioLoop();
                break;

            case "4":
                /*environmentSe*/
                break; 
        
            case "q":
                controller.shutdown();
                System.exit(0);
                break;

            default:
                break;
        }
        mainLoop();
        // the main loop must show a menu where the user:
        // - stimulates the fake environment (or just manage the logic of this, you choose);
        // - schedules commands (even repeated Tasks);
        // - applies scenarios;
        // - shut down simulation

        // you are free to add other stuff if you have idea
    }

    private void showDevicesLoop() {

        gui.printShowDevice(controller);
        scan.nextLine();
        deviceConfigLoop();
    }

    private void removeDeviceLoop() {

        gui.printRemoveDevice(controller);
        String devName;
        
        switch (devName = scan.nextLine()) {
            case "":
                deviceConfigLoop();
                break;
            default:
                controller.removeDevice(controller.getDeviceFromName(devName));
                break;
        }
        removeDeviceLoop();
    }

    private void addDeviceLoop() {
        
        gui.printAddDevice(controller);
        String typeName;
        String devName;
        
        switch (typeName = scan.nextLine()) {

            case "":
                deviceConfigLoop();
                break;

            default:
                if (!devFactory.lookFor(typeName)) {
                    System.out.println("This device type is not supported.");
                    break;
                }

                System.out.println("Type your new device name.");
                System.out.print(">> ");
                devName = scan.nextLine();
                controller.addDevice(devFactory.createDevice(typeName, devName));
                break;
        }

        addDeviceLoop();
    }

    private void deviceMonitoringLoop() {

        gui.printDeviceMonitoring(controller);
        String devName;
        
        switch (devName = scan.nextLine()) {
            case "":
                deviceConfigLoop();
                break;
            default:
                if (controller.getDeviceFromName(devName) instanceof ObservableDevice od) {
                    controller.toggleDeviceMonitoring(od);
                }
                break;
        }
        deviceMonitoringLoop();
    }

    // method is too big, maybe split in smaller methods? 
    // and with maybe, i mean that i don't care, but Single Responsibility Principle exists...
    private void scheduleACommandLoop() {
        String devName;
        gui.printCommandScheduler(controller);
        switch (devName = scan.nextLine()) {
            
            case "":

                mainLoop();
                break;
            
            default:    
                System.out.println("available commands for " + devName + ":");
                if(controller.getDeviceFromName(devName) == null) {
                    System.out.println("This device does not exist.");
                    scheduleACommandLoop();
                    break;
                }
                cmdRegister.getAvailableCommands(controller.getDeviceFromName(devName).getBaseType()).stream().forEach(cmd -> System.out.println("\t" + cmd));
                System.out.println("what command do you want to schedule?");
                System.out.print(">>");
                String cmdName = scan.nextLine();
                Command cmd = cmdFactory.createCommand(cmdName);
                if(cmd == null) {
                    System.out.println("The command is not recognized.");
                    scheduleACommandLoop();
                    break;
                }
                long delaySecs, repeatSecs;
                System.out.println("how long until the command is executed? (in seconds)");
                // can't we parse a date? like 20:30? it can be useful
                System.out.print(">>");
                try {
                    delaySecs = scan.nextLong()+1;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, using default value of 1 second...");
                    scan.nextLine(); // clear the buffer
                    delaySecs = 1;
                }
                System.out.println("how often shall the command be executed? (in seconds)");
                System.out.print(">>");
                try {
                    repeatSecs = scan.nextLong();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, using default value of 0 seconds...");
                    scan.nextLine(); // clear the buffer
                    repeatSecs = 0;
                }     
                controller.scheduleCommand(devName, delaySecs, repeatSecs, cmd);
                break;
            }
    }

    private void addAFunctionalityLoop() {
        
        gui.printAddAFunctionality(controller);
        String devName;
        
        switch (devName = scan.nextLine()) {
            case "":
                deviceConfigLoop();
                break;

            default:
                if (controller.getDeviceFromName(devName) == null) {
                    break;
                }
                
                System.out.println("which function you want to add?");
                String decName = scan.nextLine();
                Device decoratedDev = decFactory.addFunctionality(controller.getDeviceFromName(devName), decName);

                if (decoratedDev == null) {
                    System.out.println("This functionality is not supported.");
                    break;
                }
                controller.updateFunctionality(devName, decoratedDev);
                
                break;
        }

        addAFunctionalityLoop();
    }

    private void scenariosMenuLoop() {
        gui.printScenariosMenu();
        switch (scan.nextLine()) {
            case "1":
                createScenarioLoop();
                break;

            case "2":
                scheduleScenarioLoop();
                break;

            case "3":
                triggerAScenarioLoop();
                break;

            case "4":
                removeScenarioLoop();
                break;  

            case "":
                mainLoop();
                break;
                
            default:
                break;
        }
        deviceConfigLoop();
    }

    private void removeScenarioLoop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeScenarioLoop'");
    }

    private void scheduleScenarioLoop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'scheduleScenarioLoop'");
    }

    private void createScenarioLoop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createScenarioLoop'");
    }

    private void triggerAScenarioLoop() {
        
        gui.printTriggerScenario();
        String scenarioName;

        switch (scenarioName = scan.nextLine()) {
            case "":
                mainLoop();
                break;
            default:
                /*... */
                break;
        }
        triggerAScenarioLoop();
    }

}