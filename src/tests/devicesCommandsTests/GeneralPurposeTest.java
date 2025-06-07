package tests.devicesCommandsTests;

import org.junit.Test;

import devices.camera.*;
import devices.speaker.*;
import commands.generalPurposeCommands.*;
import commands.Command;

import static org.junit.Assert.*;

public class GeneralPurposeTest {
    
    @Test
    public void TurnOnCommandTest() {
        Camera c = new BaseCamera("TestCamera");
        Speaker s = new SpotifyApp(new BaseSpeaker("TestSpeaker"));

        Command tc = new TurnOnCommand();
        c.performAction(tc);
        s.performAction(tc);

        assertEquals(true, s.isOn() && c.isOn());
    }
}
