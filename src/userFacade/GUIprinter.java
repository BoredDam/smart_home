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
        guiWindow.print("|                          3)       kill command                               |");
        guiWindow.print("|                          4)      scenarios menu                              |");
        guiWindow.print("|                          5)    environment setting                           |");
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
        guiWindow.print(controller.deviceListToString(""));
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                   write the name of the device you want to                   |");
        guiWindow.print("|                           schedule a command for                             |");
        guiWindow.print("|          Note: devices that are turned off will not perform any command!     |");
        guiWindow.print("|                      Turn them on first with TurnOnCommand!                  |");
        guiWindow.print("+------------------------------------------------------------------------------+");
    }

    public void printKillCommand(SmartHomeController controller) {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                               KILL A COMMAND                                 |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print(controller.scheduledCommandsToString());
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                write the index of the command you want to kill               |");
        guiWindow.print("+------------------------------------------------------------------------------+");
    }
    public void printAddDevice(SmartHomeController controller, String availableTypes) {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                           ADD A DEVICE TO THE LIST                           |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print(controller.deviceListToString(""));
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                  1.write the type of a device you want to add                |");
        guiWindow.print("|                      2.write the name of your new device                     |");
        guiWindow.print("|                          also, no duplicate names!                           |");
        guiWindow.print("|                       to go back, don't write anything                       |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print(availableTypes);
    }

    public void printDeviceMonitoring(SmartHomeController controller) {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                            SET DEVICE MONITORING                             |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print(controller.deviceListToString(""));
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
        guiWindow.print(controller.deviceListToString(""));
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
        guiWindow.print(controller.deviceListToString(""));
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

    public void printAddAFunctionality(SmartHomeController controller) {
        guiWindow.clear();
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                             ADD A FUNCTIONALITY                              |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print(controller.deviceListToString(""));
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
        guiWindow.print("|                          1)     show scenarios                               |");
        guiWindow.print("|                          2)    create a scenario                             |");
        guiWindow.print("|                          3)     edit a scenario                              |");
        guiWindow.print("|                          4)    schedule a scenario                           |");
        guiWindow.print("|                          5)    trigger a scenario                            |");
        guiWindow.print("|                          6)     remove a scenario                            |");
        guiWindow.print("|                          7) show scheduled scenarios                         |");
        guiWindow.print("|                           )  back to the config menu                         |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
    }

    private void printScenariosList(List<Scenario> userScenarios) {
        userScenarios.stream().forEach(scenario -> guiWindow.print("! " + scenario.getName()));
    }
    public void printShowScenarios(List<Scenario> userScenarios) {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                                  SCENARIOS LIST                              |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        printScenariosList(userScenarios);
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                        any) back to the scenarios menu                       |");
        guiWindow.print("+------------------------------------------------------------------------------+");
    }

    public void printRemoveScenario(List<Scenario> userScenarios) {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                     REMOVE SCENARIO FROM SCENARIOS LIST                      |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        printScenariosList(userScenarios);
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                     write the name of a scenario to remove                   |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                        to go back, don't write anything                      |");
        guiWindow.print("+------------------------------------------------------------------------------+");
    }
    public void printCreateScenario(List<Scenario> userScenarios) {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                       ADD SCENARIO FROM SCENARIOS LIST                       |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        printScenariosList(userScenarios);
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                      write the name of a scenario to add                     |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                        to go back, don't write anything                      |");
        guiWindow.print("|              Note: scenarios with the same names are not allowed!            |");
        guiWindow.print("+------------------------------------------------------------------------------+");
    }

    public void printScenarioScheduler(List<Scenario> userScenarios) {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                      SCHEDULE SCENARIO FROM SCENARIOS LIST                   |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        printScenariosList(userScenarios);
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                write the name of a scenario you want to schedule             |");
        guiWindow.print("|                     the scenario will then repeat every day                  |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                        to go back, don't write anything                      |");
        guiWindow.print("+------------------------------------------------------------------------------+");
    }

    public void printTriggerScenario(List<Scenario> userScenarios) {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                       TRIGGER SCENARIO FROM SCENARIOS LIST                   |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        printScenariosList(userScenarios);
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                write the name of a scenario you want to trigger              |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                        to go back, don't write anything                      |");
        guiWindow.print("+------------------------------------------------------------------------------+");
    }
    public void printEditScenario(List<Scenario> userScenarios) {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                       EDIT A SCENARIO FROM SCENARIOS LIST                    |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        printScenariosList(userScenarios);
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                 write the name of a scenario you want to edit                |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                        to go back, don't write anything                      |");
        guiWindow.print("+------------------------------------------------------------------------------+");
    }

    public void printInternalScenarioEdit(String scenarioName) {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                       EDITING SCENARIO INTERNAL SETTINGS                     |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                 1)           Change scenario name                            |");
        guiWindow.print("|                 2)       Add a command to the scenario                       |");
        guiWindow.print("|                 3)    Remove a command from the scenario                     |");
        guiWindow.print("|                 4) Enable device monitoring for the scenario                 |");
        guiWindow.print("|                 5) Disable device monitoring for the scenario                |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                        to go back, don't write anything                      |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("                                 Chosen scenario: " + scenarioName);
    }
    
    public void printScheduledScenarios(SmartHomeController controller) {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                          SCHEDULED SCENARIOS LIST                            |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print(controller.scheduledScenariosToString());
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|            Scheduled scenarios will repeat every day at the same time        |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
    }

    public void printEnvironmentSettings() {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                            ENVIRONMENT SETTINGS                              |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|           1) calculate temperature and make thermostat measure it            |");
        guiWindow.print("|           2)                     open a door                                 |");
        guiWindow.print("|           3)                     close a door                                |");
        guiWindow.print("|           4)             make camera detect a presence                       |");
        guiWindow.print("|            )                 back to the main menu                           |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
    }

    public void printOpenDoor(SmartHomeController controller) {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                             OPEN A DOOR                                      |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print(controller.deviceListToString("Door"));
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                write the name of a door you want to open                     |");
        guiWindow.print("|             write 'random' if you want to open a random door                 |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                    to go back, don't write anything                          |");
        guiWindow.print("+------------------------------------------------------------------------------+");
    }

    public void printCloseDoor(SmartHomeController controller) {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                             CLOSE A DOOR                                     |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print(controller.deviceListToString("Door"));
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                write the name of a door you want to close                    |");
        guiWindow.print("|             write 'random' if you want to close a random door                |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                    to go back, don't write anything                          |");
        guiWindow.print("+------------------------------------------------------------------------------+");
    }

    public void printCameraPresenceDetection(SmartHomeController controller) {
        guiWindow.clear();
        guiWindow.print("\n");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                        CAMERA PRESENCE DETECTION                             |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print(controller.deviceListToString("Camera"));
        guiWindow.print("+------------------------------------------------------------------------------+");
        guiWindow.print("|            write the name of a camera you want to enable presence            |");
        guiWindow.print("|         detection for, or write 'random' to enable it for a random one       |");
        guiWindow.print("|                                                                              |");
        guiWindow.print("|                    to go back, don't write anything                          |");
        guiWindow.print("+------------------------------------------------------------------------------+");
    }
}
