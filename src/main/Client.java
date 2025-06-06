package main;

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
        controller.printDeviceList();
    }
}