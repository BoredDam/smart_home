package userFacade;
import java.util.function.Consumer;

/**
 * GUIPrinter is the class responsible of printing the user interface for the entire software.
 * The GUIPrinter methods get called by the UserFacade class, which implements the logic 
 * behind the selection of the menu to print.
 * 
 * @author Paolo Volpini
 * @author Damiano Trovato
 */

public class GUIPrinter {
    private final GUIWindow guiWindow;

    private final int textWidth;
    public GUIPrinter(GUIWindow guiWindow) {
        this.guiWindow = guiWindow;
        this.textWidth = guiWindow.getWidth() / 9;
    }

    public void printToWindow(String msg) {
        guiWindow.print(msg);
    }
    
    public void setMenu(Consumer<String> handler) {
        guiWindow.setInputHandler(handler);
    }

    /**
     * Centers a line of text based on the textWidth of the window.
     * @param text the text to center
     * @param textWidth the textWidth of the window
     * @return a centered line with borders
     */
    private String centerLine(String text, int textWidth) {
        int contentWidth = textWidth - 2; // 2 for borders
        int padding = (contentWidth - text.length()) / 2;
        int extra = (contentWidth - text.length()) % 2;
        return "|" + " ".repeat(padding) + text + " ".repeat(padding + extra) + "|";
    }

    public void printMainMenu() {
        guiWindow.clear();
        guiWindow.print("\n");

        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("MAIN MENU", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
        guiWindow.print(centerLine("1)    configuration menu ", textWidth));
        guiWindow.print(centerLine("2)    schedule a command ", textWidth));
        guiWindow.print(centerLine("3)       kill command    ", textWidth));
        guiWindow.print(centerLine("4)      scenarios menu   ", textWidth));
        guiWindow.print(centerLine("5)    environment setting", textWidth));
        guiWindow.print(centerLine("q)        shutdown       ", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
    }

        // Helper method to center a line within the given textWidth
        

    public void printCommandScheduler(String deviceList) {
        guiWindow.clear();
        guiWindow.print("\n");
        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("SCHEDULE A COMMAND", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
        guiWindow.print(deviceList);
        guiWindow.print(border);
        guiWindow.print(centerLine("write the name of the device you want to", textWidth));
        guiWindow.print(centerLine("schedule a command for", textWidth));
        guiWindow.print(centerLine("Note: devices that are turned off will not perform any command!", textWidth));
        guiWindow.print(centerLine("Turn them on first with TurnOnCommand!", textWidth));
        guiWindow.print(border);
    }

    public void printKillCommand(String scheduledCommands) {
        guiWindow.clear();
        guiWindow.print("\n");
        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("KILL A COMMAND", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
        guiWindow.print(scheduledCommands);
        guiWindow.print(border);
        guiWindow.print(centerLine("write the index of the command you want to kill", textWidth));
        guiWindow.print(border);
    }

    public void printAddDevice(String deviceList, String availableTypes) {
        guiWindow.clear();
        guiWindow.print("\n");
        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("ADD A DEVICE TO THE LIST", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
        guiWindow.print(deviceList);
        guiWindow.print(border);
        guiWindow.print(centerLine("1.write the type of a device you want to add", textWidth));
        guiWindow.print(centerLine("2.write the name of your new device", textWidth));
        guiWindow.print(centerLine("also, no duplicate names!", textWidth));
        guiWindow.print(centerLine("to go back, don't write anything", textWidth));
        guiWindow.print(border);
        guiWindow.print(centerLine("Available device types:", textWidth));
        guiWindow.print(centerLine(availableTypes, textWidth));
        guiWindow.print(border);
    }

    public void printDeviceMonitoring(String deviceList) {
        guiWindow.clear();
        guiWindow.print("\n");
        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("SET DEVICE MONITORING", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
        guiWindow.print(deviceList);
        guiWindow.print(border);
        guiWindow.print(centerLine("monitored devices will react to events and", textWidth));
        guiWindow.print(centerLine("notify the SmartHomeController", textWidth));
        guiWindow.print(border);
    }

    public void printRemoveDevice(String deviceList) {
        guiWindow.clear();
        guiWindow.print("\n");
        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("REMOVE A DEVICE FROM THE LIST", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
        guiWindow.print(deviceList);
        guiWindow.print(border);
        guiWindow.print(centerLine("write the name of a device to remove", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("to go back, don't write anything", textWidth));
        guiWindow.print(border);
    }

    public void printShowDevice(String deviceList) {
        guiWindow.clear();
        guiWindow.print("\n");
        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("DEVICE LIST", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
        guiWindow.print(deviceList);
        guiWindow.print(border);
        guiWindow.print(centerLine("any) back to the config menu", textWidth));
        guiWindow.print(border);
    }

    public void printDeviceConfig() {
        guiWindow.clear();
        guiWindow.print("\n");
        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("CONFIGURATION MENU", textWidth));
        guiWindow.print(centerLine("Here you are able to configure devices, functionalities and scenarios", textWidth));
        guiWindow.print("|" + "-".repeat(textWidth - 2) + "|");
        guiWindow.print(centerLine("1)   show connected devices", textWidth));
        guiWindow.print(centerLine("2)       add a device      ", textWidth));
        guiWindow.print(centerLine("3)     remove a device     ", textWidth));
        guiWindow.print(centerLine("4)    add a functionality  ", textWidth));
        guiWindow.print(centerLine("5)    set device monitoring", textWidth));
        guiWindow.print(centerLine(")    back to the main menu ", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
    }

    public void printAddAFunctionality(String deviceList, String availableTypes) {
        guiWindow.clear();
        guiWindow.print("\n");
        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("ADD A FUNCTIONALITY", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
        guiWindow.print(deviceList);
        guiWindow.print(border);
        guiWindow.print(centerLine("Available additional functionalities:", textWidth));
        guiWindow.print(availableTypes);
        guiWindow.print(border);
    }
    
    public void printSeparator() {
        guiWindow.print(System.lineSeparator() + System.lineSeparator());
    }

    public void printScenariosMenu() {
        guiWindow.clear();
        guiWindow.print("\n");
        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("SCENARIOS MENU", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
        guiWindow.print(centerLine("1)     show scenarios      ", textWidth));
        guiWindow.print(centerLine("2)    create a scenario    ", textWidth));
        guiWindow.print(centerLine("3)     edit a scenario     ", textWidth));
        guiWindow.print(centerLine("4)    schedule a scenario  ", textWidth));
        guiWindow.print(centerLine("5)    trigger a scenario   ", textWidth));
        guiWindow.print(centerLine("6)     remove a scenario   ", textWidth));
        guiWindow.print(centerLine("7) show scheduled scenarios", textWidth));
        guiWindow.print(centerLine("8)  kill scheduled scenario", textWidth));
        guiWindow.print(centerLine(")  back to the config menu ", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
    }

    public void printShowScenarios(String userScenarios) {
        guiWindow.clear();
        guiWindow.print("\n");
        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("SCENARIOS LIST", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
        guiWindow.print(userScenarios);
        guiWindow.print(border);
        guiWindow.print(centerLine("any) back to the scenarios menu", textWidth));
        guiWindow.print(border);
    }

    public void printRemoveScenario(String userScenarios) {
        guiWindow.clear();
        guiWindow.print("\n");
        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("REMOVE SCENARIO FROM SCENARIOS LIST", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
        guiWindow.print(userScenarios);
        guiWindow.print(border);
        guiWindow.print(centerLine("write the name of a scenario to remove", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("to go back, don't write anything", textWidth));
        guiWindow.print(border);
    }

    public void printCreateScenario(String userScenarios) {
        guiWindow.clear();
        guiWindow.print("\n");
        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("ADD SCENARIO FROM SCENARIOS LIST", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
        guiWindow.print(userScenarios);
        guiWindow.print(border);
        guiWindow.print(centerLine("write the name of a scenario to add", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("to go back, don't write anything", textWidth));
        guiWindow.print(centerLine("Note: scenarios with the same names are not allowed!", textWidth));
        guiWindow.print(border);
    }

    public void printScenarioScheduler(String userScenarios) {
        guiWindow.clear();
        guiWindow.print("\n");
        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("SCHEDULE SCENARIO FROM SCENARIOS LIST", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
        guiWindow.print(userScenarios);
        guiWindow.print(border);
        guiWindow.print(centerLine("write the name of a scenario you want to schedule", textWidth));
        guiWindow.print(centerLine("the scenario will then repeat every day", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("to go back, don't write anything", textWidth));
        guiWindow.print(border);
    }

    public void printTriggerScenario(String userScenarios) {
        guiWindow.clear();
        guiWindow.print("\n");
        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("TRIGGER SCENARIO FROM SCENARIOS LIST", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
        guiWindow.print(userScenarios);
        guiWindow.print(border);
        guiWindow.print(centerLine("write the name of a scenario you want to trigger", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("to go back, don't write anything", textWidth));
        guiWindow.print(border);
    }

    public void printEditScenario(String userScenarios) {
        guiWindow.clear();
        guiWindow.print("\n");
        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("EDIT A SCENARIO FROM SCENARIOS LIST", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
        guiWindow.print(userScenarios);
        guiWindow.print(border);
        guiWindow.print(centerLine("write the name of a scenario you want to edit", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("to go back, don't write anything", textWidth));
        guiWindow.print(border);
    }

    public void printInternalScenarioEdit(String scenarioName) {
        guiWindow.clear();
        guiWindow.print("\n");
        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("EDITING SCENARIO INTERNAL SETTINGS", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
        guiWindow.print(centerLine("1)           Change scenario name            ", textWidth));
        guiWindow.print(centerLine("2)       Add a command to the scenario       ", textWidth));
        guiWindow.print(centerLine("3)    Remove a command from the scenario     ", textWidth));
        guiWindow.print(centerLine("4) Enable device monitoring for the scenario ", textWidth));
        guiWindow.print(centerLine("5) Disable device monitoring for the scenario", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("to go back, don't write anything", textWidth));
        guiWindow.print(border);
        guiWindow.print(centerLine("Chosen scenario: " + scenarioName, textWidth));
    }
    
    public void printScheduledScenarios(String scheduledScenarios) {
        guiWindow.clear();
        guiWindow.print("\n");
        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("SCHEDULED SCENARIOS LIST", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
        guiWindow.print(scheduledScenarios);
        guiWindow.print(border);
        guiWindow.print(centerLine("Scheduled scenarios will repeat every day at the same time", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
    }

    public void printKillScheduledScenario(String scheduledScenarios) {
        guiWindow.clear();
        guiWindow.print("\n");
        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("KILL SCHEDULED SCENARIO", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
        guiWindow.print(scheduledScenarios);
        guiWindow.print(border);
        guiWindow.print(centerLine("write the name of the scheduled scenario you want to kill", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("to go back, don't write anything", textWidth));
        guiWindow.print(border);
    }

    public void printEnvironmentSettings() {
        guiWindow.clear();
        guiWindow.print("\n");
        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("ENVIRONMENT SETTINGS", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
        guiWindow.print(centerLine("1) calculate temperature and make thermostat measure it", textWidth));
        guiWindow.print(centerLine("2)                     open a door                     ", textWidth));
        guiWindow.print(centerLine("3)                     close a door                    ", textWidth));
        guiWindow.print(centerLine("4)             make camera detect a presence           ", textWidth));
        guiWindow.print(centerLine(")                 back to the main menu                ", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
    }

    public void printOpenDoor(String doorList) {
        guiWindow.clear();
        guiWindow.print("\n");
        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("OPEN A DOOR", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
        guiWindow.print(doorList);
        guiWindow.print(border);
        guiWindow.print(centerLine("write the name of a door you want to open", textWidth));
        guiWindow.print(centerLine("write 'random' if you want to open a random door", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("to go back, don't write anything", textWidth));
        guiWindow.print(border);
    }

    public void printCloseDoor(String doorList) {
        guiWindow.clear();
        guiWindow.print("\n");
        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("CLOSE A DOOR", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
        guiWindow.print(doorList);
        guiWindow.print(border);
        guiWindow.print(centerLine("write the name of a door you want to close", textWidth));
        guiWindow.print(centerLine("write 'random' if you want to close a random door", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("to go back, don't write anything", textWidth));
        guiWindow.print(border);
    }

    public void printCameraPresenceDetection(String cameraList) {
        guiWindow.clear();
        guiWindow.print("\n");
        String border = "+" + "-".repeat(textWidth - 2) + "+";
        guiWindow.print(border);
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("CAMERA PRESENCE DETECTION", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(border);
        guiWindow.print(cameraList);
        guiWindow.print(border);
        guiWindow.print(centerLine("write the name of a camera you want to enable presence", textWidth));
        guiWindow.print(centerLine("detection for, or write 'random' to enable it for a random one", textWidth));
        guiWindow.print(centerLine("", textWidth));
        guiWindow.print(centerLine("to go back, don't write anything", textWidth));
        guiWindow.print(border);
    }
}
