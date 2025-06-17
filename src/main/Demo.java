package main;

import commands.doorCommands.LockCommand;
import commands.generalPurposeCommands.TurnOffCommand;
import commands.lightCommands.SwitchLightCommand;
import controller.SmartHomeController;
import devices.adapter.OldHeaterAdapter;
import devices.airConditioner.AirConditioner;
import devices.camera.BaseCamera;
import devices.camera.Camera;
import devices.camera.HDAudio;
import devices.camera.ThermalVision;
import devices.door.Door;
import devices.light.Light;
import devices.speaker.BaseSpeaker;
import devices.speaker.Speaker;
import devices.speaker.SpotifyApp;
import devices.thermostat.Thermostat;
import userFacade.GUIWindow;
import userFacade.UserFacade;

public class Demo {
    private static void addDemoData() {
        // Creates devices for the demo
        SmartHomeController controller = SmartHomeController.getInstance(); // this is the same instance as in the GUIWindow since it's a singleton
        Thermostat t1 = new Thermostat("thermo1");
        Thermostat t2 = new Thermostat("thermo2");
        Camera c1 = new BaseCamera("cam1");
        Camera c2 = new HDAudio(new BaseCamera("cam2"));
        Camera c3 = new ThermalVision(new BaseCamera("cam3"));
        Door d1 = new Door("door1");
        Door d2 = new Door("door2");
        Speaker s1 = new BaseSpeaker("sp1");
        Speaker s2 = new SpotifyApp(new BaseSpeaker("sp2"));
        AirConditioner ac1 = new AirConditioner("ac1");
        AirConditioner ac2 = new AirConditioner("ac2");
        OldHeaterAdapter oha = new OldHeaterAdapter("oldHeater");
        Light l1 = new Light("l1");
        t1.turnOn();
        c1.turnOn();
        c2.turnOn();
        d1.turnOn();
        s2.turnOn();
        ac1.turnOn();
        t1.setLowerBound(18.0f);
        t1.setUpperBound(30.0f);
        oha.turnOn();
        
        controller.addDevice(t1);
        controller.addDevice(t2);
        controller.addDevice(c1);
        controller.addDevice(c2);
        controller.addDevice(c3);
        controller.addDevice(d1);
        controller.addDevice(d2);
        controller.addDevice(s1);
        controller.addDevice(s2);
        controller.addDevice(ac1);
        controller.addDevice(ac2);
        controller.addDevice(l1);
        controller.addDevice(oha);
        controller.setupDefaultEvents();
        controller.addScenario("Modalità notte");
        controller.addCommandToScenario("l1", 1, 0, new TurnOffCommand(), "Modalità notte");
        controller.addCommandToScenario("door1", 1, 0, new LockCommand(), "Modalità notte");
        controller.addCommandToScenario("door2", 1, 0, new LockCommand(), "Modalità notte");

        controller.addScenario("Modalità vacanza");
        controller.addCommandToScenario("l1", 0, 239, new SwitchLightCommand(), "Modalità vacanza");

    }
    
    public static void main(String[] args) throws InterruptedException {
        UserFacade menu = new UserFacade(new GUIWindow());
        menu.initializeUserSpace();
        menu.mainDialog();
        addDemoData();
    }

}
