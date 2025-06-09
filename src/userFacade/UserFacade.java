package userFacade;

import controller.SmartHomeController;
import debugTools.Environment;
import devices.Device;
import factory.DeviceFactory;
import java.io.IOException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class UserFacade {
    
    private Environment simulation;
    private SmartHomeController controller;
    private Scanner scan;
    private DeviceFactory devFactory;

    public UserFacade() {
    }

    private void initializeDefaultController() {

        System.out.println("Setting default controller...");
        controller = SmartHomeController.getInstance();
        devFactory = DeviceFactory.getInstance();
        scan = new Scanner(System.in);
    }

    public void mainDialog() {
        // if we have more than one type of controller, we can further modify this. But this is not the case 
        // and we just leave the possibility to extend the software
        System.out.println("Welcome! This is a Smart Home simulator. We will now setup the basic environment for your simulation...");
        initializeDefaultController();
        printSeparator();
        System.out.println("Everything should be in place!");
        printSeparator();
        mainLoop();
    }

    private void configLoop() {
        clearScreen();
        System.out.println("\n");
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                               CONFIGURATION MENU                             |");
        System.out.println("|     Here you are able to configure devices, functionalities and scenarios    |");
        System.out.println("|------------------------------------------------------------------------------|");
        System.out.println("|                          1)   show connected devices                         |");
        System.out.println("|                          2)      add a device                                |");
        System.out.println("|                          3)     remove a device                              |");
        System.out.println("|                          4)    add a functionality                           |");
        System.out.println("|                          5)    set device monitoring                         |");
        System.out.println("|                          6)    schedule a command                            |");
        System.out.println("|                          7)       scenarios menu                             |");
        System.out.println("|                          b)    back to the main menu                         |");
        System.out.println("|                                                                              |");
        System.out.println("+------------------------------------------------------------------------------+");
          System.out.print(">> ");

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
                /*TO-DO*/
                break; 
        
            case "b":
                mainLoop();
                break;
                
            default:
                configLoop();
                break;
        }

        /*
            // keep in mind that:
            // - there must be NO scenarios that share the same name;
            // after a keyword (like ready, or endSetup, or something else), ask what scenarios
            // should be applied, apply it and then call controller.setupDefaultEvents() and return.
        }
         */
    }

    private void mainLoop() {
        clearScreen();
        System.out.println("\n");
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                                                                              |");
        System.out.println("|                                   MAIN MENU                                  |");
        System.out.println("|                                                                              |");
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                          1)    configuration menu                            |");
        System.out.println("|                          2)    schedule a command                            |");
        System.out.println("|                          3)    trigger a scenario                            |");
        System.out.println("|                          4)    environment setting                           |");
        System.out.println("|                          q)        shutdown                                  |");
        System.out.println("|                                                                              |");
        System.out.println("+------------------------------------------------------------------------------+");
          System.out.print(">> ");
        
        switch (scan.nextLine()) {
            case "1":
                configLoop();
                break;

            case "2":
                /*TO-DO*/
                break;

            case "3":
                
                break;

            case "4":
                /*TO-DO*/
                break; 
        
            case "q":
                controller.shutdown();
                System.exit(0);
                break;

            default:
                mainLoop();
                break;
        }
        // the main loop must show a menu where the user:
        // - stimulates the fake environment (or just manage the logic of this, you choose);
        // - schedules commands (even repeated Tasks);
        // - applies scenarios;
        // - shut down simulation

        // you are free to add other stuff if you have idea
    }

    private void showDevicesLoop() {
        clearScreen();
        System.out.println("\n");
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                                                                              |");
        System.out.println("|                                 DEVICE LIST                                  |");
        System.out.println("|                                                                              |");
        System.out.println("+------------------------------------------------------------------------------+");
        controller.printDeviceList();
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                        any) back to the config menu                          |");
        System.out.println("+------------------------------------------------------------------------------+");
          System.out.print(">> ");
        scan.nextLine();
        configLoop();
    }

    private void removeDeviceLoop() {
        clearScreen();
        System.out.println("\n");
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                                                                              |");
        System.out.println("|                                 DEVICE LIST                                  |");
        System.out.println("|                                                                              |");
        System.out.println("+------------------------------------------------------------------------------+");
        controller.printDeviceList();
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                      write the name of a device to remove                    |");
        System.out.println("|                  dont write anything, if you want to go back                 |");
        System.out.println("+------------------------------------------------------------------------------+");

        boolean gotRemoved = true;
        String devName;
        do {
            System.out.print(">> ");
            switch (devName = scan.nextLine()) {
                case "":
                    configLoop();
                    break;

                default:
                    gotRemoved = controller.removeDevice(controller.getDeviceFromName(devName));
                    break;
            }
        } while(gotRemoved == false);
        removeDeviceLoop();
    }

    private void addDeviceLoop() {
        printSeparator();
        System.out.println("\n");
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                                                                              |");
        System.out.println("|                                 DEVICE LIST                                  |");
        System.out.println("|                                                                              |");
        System.out.println("+------------------------------------------------------------------------------+");
        controller.printDeviceList();
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                   write the type of a device you want to add                 |");
        System.out.println("|                   dont write anything if you want to go back                 |");
        System.out.println("|                          P.S. no duplicate names!                            |");
        System.out.println("+------------------------------------------------------------------------------+");
          System.out.print(">> ");
        String typeName;
        String devName;
        
        switch (typeName = scan.nextLine()) {
            case "":
                configLoop();
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

    private void printSeparator(){
        System.out.println(System.lineSeparator() + System.lineSeparator());
    }

    private void clearScreen(){
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) { 
            
        }
    }

}
