package main;

import commands.Command;
import commands.generalPurposeCommands.TurnOnCommand;
import commands.speaker.PlayCommand;
import controller.SmartHomeController;
import devices.speaker.BaseSpeaker;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        BaseSpeaker cassa = new BaseSpeaker("cassa");
        SmartHomeController controller = SmartHomeController.getInstance();
        BaseSpeaker cassa2 = new BaseSpeaker("cass2");
        controller.addDevice(cassa);
        controller.addDevice(cassa2);
        controller.printDeviceList();
        controller.removeDevice(cassa2);
        Command riproduci = new PlayCommand();
        System.out.println("We should not get anything now...");
        controller.scheduleCommand(cassa.getName(), 0, 0, riproduci);
        Command turnOnCassa = new TurnOnCommand();
        System.out.println("Wait for 10 secs...");
        controller.scheduleCommand(cassa.getName(), 10, 0, turnOnCassa);
        controller.scheduleCommand(cassa.getName(), 12, 0, riproduci);
        controller.printDeviceList();
        Thread.sleep(14000);
        controller.shutdown();
    }
}