package userFacade;

import controller.Observer;
import controller.SmartHomeController;
import devices.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserFacade {
    // private Environment simulation... we will get here
    /* ho modificato questa interfaccia "Observer" direttamente nella classe 
     * SmartHomeController.
     */
    private SmartHomeController controller;
    private Scanner scan;

    public UserFacade() {
    }

    private void initializeDefaultController() {

        System.out.println("Setting default controller...");
        controller = SmartHomeController.getInstance();
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
        System.out.println("|     Here you are able to configure devices. The system currently supports:   |");
        System.out.println("+------------------------------------------------------------------------------+");
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
                showDevices();
                break;

            case "2":
                /* */
                break;

            case "3":
                removeDevice();
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
        while(true) {
            System.out.println("Choose an option below:"); 
            // again, show the options. The user should be able to:
            // - add a device;
            // - delete a device;
            // - add a functionality. For the functionality, check if the device supports extensions;
            // - sets the monitoring of devices;
            // - add a scenario;
            // - delete a scenario.
            // keep in mind that:
            // - there must be NO devices that share the same name;
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

    public void printDeviceList(List<Device> device_list) {
        device_list.stream().forEach(dev -> System.out.println("| " + dev.getName() + "\t\t\t\t" + dev.getClass().getSimpleName()));
    }

    private void showDevices() {
        clearScreen();
        System.out.println("\n");
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                                                                              |");
        System.out.println("|                                 DEVICE LIST                                  |");
        System.out.println("|                                                                              |");
        System.out.println("+------------------------------------------------------------------------------+");
        printDeviceList(controller.getDeviceList());
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                        any) back to the config menu                          |");
        System.out.println("+------------------------------------------------------------------------------+");
          System.out.print(">> ");
        scan.nextLine();
        configLoop();
    }

    private void removeDevice() {
        System.out.println("\n");
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                                                                              |");
        System.out.println("|                                 DEVICE LIST                                  |");
        System.out.println("|                                                                              |");
        System.out.println("+------------------------------------------------------------------------------+");
        printDeviceList(controller.getDeviceList());
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                      write the name of a device to remove                    |");
        System.out.println("+------------------------------------------------------------------------------+");
          System.out.print(">> ");
        controller.removeDevice(controller.getDeviceFromName(scan.nextLine()));
        configLoop();
    }




    private void printSeparator(){
        System.out.println();
        System.out.println();
        System.out.println();
    }

    private void clearScreen(){
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }
}
