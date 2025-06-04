package tests.unitTests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import devices.light.Light;

public class LightTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }
    
    @After
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testSwitch() {
        Light l = new Light("TestLight");
        l.lightSwitch();
        l.printState();
        String expectedOutput = "[TestLight] Turned on!" + System.lineSeparator() + "[TestLight] State: on" + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }
    
    @Test
    public void offTurnOffTest() {
        Light l = new Light("TestLight2");
        l.turnOff();
        l.printState();
        String expectedOutput = "[TestLight2] Already off!" + System.lineSeparator() + "[TestLight2] State: off" + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void doubleSwitchTest() {
        Light l = new Light("TestLight3");
        l.lightSwitch();
        l.lightSwitch();
        l.printState();
        String expectedOutput = "[TestLight3] Turned on!" + System.lineSeparator() + "[TestLight3] Turned off!" + System.lineSeparator() 
        + "[TestLight3] State: off" + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }
}
