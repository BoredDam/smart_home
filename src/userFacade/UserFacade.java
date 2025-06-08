package userFacade;

import controller.Observer;
import controller.SmartHomeController;

public class UserFacade {
    // private Environment simulation... we will get here
    private Observer controller;

    public UserFacade() {
    }

    private void initializeDefaultController() {
        System.out.println("Setting default controller...");
        controller = SmartHomeController.getInstance();
    }

    public void mainDialog() {
        // if we have more than one type of controller, we can further modify this. But this is not the case 
        // and we just leave the possibility to extend the software
        System.out.println("Welcome! This is a Smart Home simulator. We will now setup the basic environment for your simulation...");
        initializeDefaultController();
        System.out.println("Everything should be in place!");
        setupLoop();
        mainLoop();
    }

    private void setupLoop() {
        System.out.println("---- Configuration menu ----");
        System.out.println("In this menu you are able to configure devices. The system currently supports:");
        // i am bored and i am not writing this.
        // The menu should output the devices available
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
    }

    private void mainLoop() {
        // the main loop must show a menu where the user:
        // - stimulates the fake environment (or just manage the logic of this, you choose);
        // - schedules commands (even repeated Tasks);
        // - applies scenarios;
        // - shut down simulation

        // you are free to add other stuff if you have idea
    }
}
