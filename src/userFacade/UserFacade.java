package userFacade;

import controller.SmartHomeController;
import debugTools.Environment;
import devices.Device;
import factory.CommandFactory;
import factory.DecoratorFactory;
import factory.DeviceFactory;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import commands.Command;
import commands.CommandRegister;

public class UserFacade {
    
    private Environment simulation;
    private SmartHomeController controller;
    private Scanner scan;
    private DeviceFactory devFactory;
    private DecoratorFactory decFactory;
    private CommandFactory cmdFactory;
    private CommandRegister cmdRegister;
    private GUIprinter gui;

    public UserFacade() {
        gui = new GUIprinter();
    }

    private void initializeDefaultController() {

        System.out.println("Setting default controller...");
        controller = SmartHomeController.getInstance();
        devFactory = DeviceFactory.getInstance();
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
                /* */
                break;
            case "b":
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
                if (!devFactory.getClassMap().containsKey(typeName)) {
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

    private void scheduleACommandLoop() {
        String devName;
        gui.printCommandScheduler(controller);
        switch (devName = scan.nextLine()) {
            
            case "":

                mainLoop();
                break;
            
            default:    
                
                System.out.println("what command do you want to schedule?");
                System.out.print(">>");
                String cmdName = scan.nextLine();
                Command cmd = cmdFactory.createCommand(cmdName);
                int delaySecs, repeatSecs;
                System.out.println("how long until the command is executed? (in seconds)");
                System.out.print(">>");
                delaySecs = scan.nextInt()+1;
                System.out.println("how often shall the command be executed? (in seconds)");
                System.out.print(">>");
                repeatSecs = scan.nextInt();
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
                System.out.println("which function you want to add?");
                String decName = scan.nextLine();
                Device decoratedDev = decFactory.addFunctionality(controller.getDeviceFromName(devName), decName);
                decoratedDev.setName(devName +" + "+ decName);
                controller.addDevice(decoratedDev);
                
                break;
        }

        addAFunctionalityLoop();
    }
}
