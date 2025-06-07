package main;

import commands.Command;
import commands.generalPurposeCommands.TurnOnCommand;
import commands.speaker.PlayCommand;
import commands.speaker.SpeakerCommand;
import controller.SmartHomeController;
import debugTools.Environment;
import devices.speaker.BaseSpeaker;

public class Client {
    public static void main(String[] args) {
        BaseSpeaker cassa = new BaseSpeaker("cassa");
        SmartHomeController controller = SmartHomeController.getInstance();
        BaseSpeaker cassa2 = new BaseSpeaker("cass2");
        controller.addDevice(cassa);
        controller.addDevice(cassa2);
        controller.printDeviceList();
        controller.removeDevice(cassa2);
        Command turnOn = new TurnOnCommand();
        turnOn.setDevice(cassa);
        SpeakerCommand riproduci = new PlayCommand();
        riproduci.setDevice(cassa);
        //controller.scheduleCommand(0, 0, turnOn);
        /*we can still send and run command on not powered devices... */
        controller.scheduleCommand(0, 0, riproduci);
        controller.printDeviceList();
        
    }
}