package userFacade;

/**
 * GUIPrinter is the class responsible of printing the user interface for the entire software.
 * The GUIPrinter methods get called by the UserFacade class, which implements the logic 
 * behind the selection of the menu to print.
 * 
 * @author Paolo Volpini
 * @author Damiano Trovato
 */

import controller.SmartHomeController;
import java.util.List;
import java.util.function.Consumer;

import scenario.Scenario;

public class GUIPrinter {

    private final GUIWindow guiWindow;
    public GUIPrinter(GUIWindow guiWindow) {
        this.guiWindow = guiWindow;
    }

    public void printToWindow(String msg) {
        guiWindow.print(msg);
    }
    
    public void setMenu(Consumer<String> handler) {
        guiWindow.setInputHandler(handler);
    }
    public void printMainMenu() {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                                   MAIN MENU                                  |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                          1)    configuration menu                            |");
        guiWindow.print("|                          2)    schedule a command                            |");
        guiWindow.print("|                          3)      scenarios menu                              |");
        guiWindow.print("|                          4)    environment setting                           |");
        guiWindow.print("|                          q)        shutdown                                  |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
    }

    public void printCommandScheduler(SmartHomeController controller) {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                              SCHEDULE A COMMAND                              |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print(controller.DeviceListToString());
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                   write the name of the device you want to                   |");
        guiWindow.print("|                           schedule a command for                             |");
        guiWindow.print("|          Note: devices that are turned off will not perform any command!     |");
        guiWindow.print("|                      Turn them on first with TurnOnCommand!                  |");
        guiWindow.print("+------------------------------------------------------------------------------+");
    }

    public void printAddDevice(SmartHomeController controller) {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                           ADD A DEVICE TO THE LIST                           |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print(controller.DeviceListToString());
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                  1.write the type of a device you want to add                |");
        guiWindow.print("|                      2.write the name of your new device                     |");
        guiWindow.print("|                          also, no duplicate names!                           |");
        guiWindow.print("|                       to go back, don't write anything                       |");
        guiWindow.print("+------------------------------------------------------------------------------+");
    }

    public void printDeviceMonitoring(SmartHomeController controller) {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                            SET DEVICE MONITORING                             |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print(controller.DeviceListToString());
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                    monitored devices will react to events and                |");
        guiWindow.print("|                       notify the SmartHomeCOntroller                         |");
        guiWindow.print("+------------------------------------------------------------------------------+");
    }

    public void printRemoveDevice(SmartHomeController controller) {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                         REMOVE A DEVICE FROM THE LIST                        |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print(controller.DeviceListToString());
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                      write the name of a device to remove                    |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                        to go back, don't write anything                      |");
        guiWindow.print("+------------------------------------------------------------------------------+");

    }

    public void printShowDevice(SmartHomeController controller) {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                                 DEVICE LIST                                  |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print(controller.DeviceListToString());
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                        any) back to the config menu                          |");
        guiWindow.print("+------------------------------------------------------------------------------+");
    }

    public void printDeviceConfig() {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                               CONFIGURATION MENU                             |");
        guiWindow.print("|     Here you are able to configure devices, functionalities and scenarios    |");
        guiWindow.print("|------------------------------------------------------------------------------|");
        guiWindow.print("|                        1)   show connected devices                           |");
        guiWindow.print("|                        2)      add a device                                  |");
        guiWindow.print("|                        3)     remove a device                                |");
        guiWindow.print("|                        4)    add a functionality                             |");
        guiWindow.print("|                        5)    set device monitoring                           |");
        guiWindow.print("|                         )    back to the main menu                           |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
    }

    public void printTriggerScenario() {
        guiWindow.clear();
        guiWindow.print("WIP");
    }

    public void printAddAFunctionality(SmartHomeController controller) {
        guiWindow.clear();
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                             ADD A FUNCTIONALITY                              |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print(controller.DeviceListToString());
        guiWindow.print("+------------------------------------------------------------------------------+");
    }
    
    public void printSeparator() {
        guiWindow.print(System.lineSeparator() + System.lineSeparator());
    }

    public void printScenariosMenu() {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                                  SCENARIOS MENU                              |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                          1)    show scenarios loop                           |");
        guiWindow.print("|                          2)     create a scenario                            |");
        guiWindow.print("|                          3)    schedule a scenario                           |");
        guiWindow.print("|                          4)    trigger a scenario                            |");
        guiWindow.print("|                          5)     remove a scenario                            |");
        guiWindow.print("|                           )  back to the config menu                         |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
    }

    public void printShowScenarios(List<Scenario> userScenario) {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                                  SCENARIOS LIST                              |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        userScenario.stream().forEach(scenario -> guiWindow.print("| "+ scenario));
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                        any) back to the scenarios menu                       |");
        guiWindow.print("+------------------------------------------------------------------------------+");
    }

    public void printCreateScenario() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printCreateScenario'");
    }

    public void printEditScenario() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printEditScenario'");
    }

    
}
