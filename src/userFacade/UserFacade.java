package userFacade;

import controller.SmartHomeController;
import debugTools.Environment;
import devices.Device;
import factory.DecoratorFactory;
import factory.DeviceFactory;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class UserFacade {
    
    private Environment simulation;
    private SmartHomeController controller;
    private Scanner scan;
    private DeviceFactory devFactory;
    private DecoratorFactory decFactory;
    private GUIprinter gui;

    public UserFacade() {
        gui = new GUIprinter();
    }

    private void initializeDefaultController() {

        System.out.println("Setting default controller...");
        controller = SmartHomeController.getInstance();
        devFactory = DeviceFactory.getInstance();
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
        removeDeviceLoop();
    }

    private void scheduleACommandLoop() {
        
    }

    private void addAFunctionalityLoop() {
        gui.printAddAFunctionality(controller);
        String devName;
        
        switch (devName = scan.nextLine()) {
            case "":
                deviceConfigLoop();
                break;
            default:
                /*WIP DECORATION LOGIC*/
                break;
        }
        addAFunctionalityLoop();

    }
}
