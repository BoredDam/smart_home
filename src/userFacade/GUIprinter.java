package userFacade;

import controller.SmartHomeController;
import java.io.IOException;

public class GUIprinter {

    public void printMainMenu() {
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
    }

    public void printCommandScheduler(SmartHomeController controller) {
        clearScreen();
        System.out.println("\n");
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                                                                              |");
        System.out.println("|                              SCHEDULE A COMMAND                              |");
        System.out.println("|                                                                              |");
        System.out.println("+------------------------------------------------------------------------------+");
        controller.printDeviceList();
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                   write the name of the device you want to                   |");
        System.out.println("|                           schedule a command for                             |");
        System.out.println("|          Note: devices that are turned off will not perform any command!     |");
        System.out.println("|                      Turn them on first with TurnOnCommand!                  |");
        System.out.println("+------------------------------------------------------------------------------+");
            System.out.print(">> ");
    }

    public void printAddDevice(SmartHomeController controller) {
        clearScreen();
        System.out.println("\n");
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                                                                              |");
        System.out.println("|                           ADD A DEVICE TO THE LIST                           |");
        System.out.println("|                                                                              |");
        System.out.println("+------------------------------------------------------------------------------+");
        controller.printDeviceList();
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                  1.write the type of a device you want to add                |");
        System.out.println("|                      2.write the name of your new device                     |");
        System.out.println("|                          also, no duplicate names!                           |");
        System.out.println("|                       to go back, don't write anything                       |");
        System.out.println("+------------------------------------------------------------------------------+");
          System.out.print(">> ");
    }

    public void printDeviceMonitoring(SmartHomeController controller) {
        clearScreen();
        System.out.println("\n");
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                                                                              |");
        System.out.println("|                            SET DEVICE MONITORING                             |");
        System.out.println("|                                                                              |");
        System.out.println("+------------------------------------------------------------------------------+");
        controller.printDeviceList();
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                    monitored devices will react to events and                |");
        System.out.println("|                       notify the SmartHomeCOntroller                         |");
        System.out.println("+------------------------------------------------------------------------------+");
          System.out.print(">> ");
    }

    public void printRemoveDevice(SmartHomeController controller) {
        clearScreen();
        System.out.println("\n");
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                                                                              |");
        System.out.println("|                         REMOVE A DEVICE FROM THE LIST                        |");
        System.out.println("|                                                                              |");
        System.out.println("+------------------------------------------------------------------------------+");
        controller.printDeviceList();
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                      write the name of a device to remove                    |");
        System.out.println("|                                                                              |");
        System.out.println("|                        to go back, don't write anything                      |");
        System.out.println("+------------------------------------------------------------------------------+");
          System.out.print(">> ");

    }

    public void printShowDevice(SmartHomeController controller) {
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
    }

    public void printDeviceConfig() {
        clearScreen();
        System.out.println("\n");
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                               CONFIGURATION MENU                             |");
        System.out.println("|     Here you are able to configure devices, functionalities and scenarios    |");
        System.out.println("|------------------------------------------------------------------------------|");
        System.out.println("|                        1)   show connected devices                           |");
        System.out.println("|                        2)      add a device                                  |");
        System.out.println("|                        3)     remove a device                                |");
        System.out.println("|                        4)    add a functionality                             |");
        System.out.println("|                        5)    set device monitoring                           |");
        System.out.println("|                        6)       scenarios menu                               |");
        System.out.println("|                         )    back to the main menu                           |");
        System.out.println("|                                                                              |");
        System.out.println("+------------------------------------------------------------------------------+");
          System.out.print(">> ");
    }

    public void printTriggerScenario() {
        clearScreen();
        System.out.println("WIP");
    }

    public void printAddAFunctionality(SmartHomeController controller) {
        clearScreen();
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                                                                              |");
        System.out.println("|                             ADD A FUNCTIONALITY                              |");
        System.out.println("|                                                                              |");
        System.out.println("+------------------------------------------------------------------------------+");
        controller.printDeviceList();
        System.out.println("+------------------------------------------------------------------------------+");
          System.out.print(">>");
    }
    
    public void printSeparator() {
        System.out.println(System.lineSeparator() + System.lineSeparator());
    }

    public void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) { 
            
        }
    }

    public void printScenariosMenu() {
        clearScreen();
        System.out.println("\n");
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                                                                              |");
        System.out.println("|                                  SCENARIOS MENU                              |");
        System.out.println("|                                                                              |");
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.println("|                          1)     create a scenario                            |");
        System.out.println("|                          2)    schedule a scenario                           |");
        System.out.println("|                          3)    trigger a scenario                            |");
        System.out.println("|                          4)     remove a scenario                            |");
        System.out.println("|                           )  back to the config menu                         |");
        System.out.println("|                                                                              |");
        System.out.println("+------------------------------------------------------------------------------+");
          System.out.print(">> ");
    }

    
}
