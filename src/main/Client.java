package main;

import controller.SmartHomeController;
import devices.speaker.BaseSpeaker;
import userFacade.GUIWindow;
import userFacade.UserFacade;

public class Client {
    public static void main(String[] args) throws InterruptedException {

        BaseSpeaker cassa = new BaseSpeaker("cassa");
        SmartHomeController controller = SmartHomeController.getInstance();
        BaseSpeaker cassa2 = new BaseSpeaker("cass2");
        controller.addDevice(cassa);
        controller.addDevice(cassa2);
        UserFacade menu = new UserFacade(new GUIWindow());
        menu.mainDialog();
    }
}