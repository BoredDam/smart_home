package tests.unitTests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import devices.door.Door;

public class DoorTest {
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
    public void doorLockOpen() {
        Door d = new Door("TestDoor");
        d.open();
        d.close();
        d.lock();
        d.open();
        String expected = "Door is already opened!" + System.lineSeparator() + "Door closed!" + System.lineSeparator() +
            "Door is now locked!" + System.lineSeparator() + "Intrusion detected!" + System.lineSeparator();
        assertEquals(expected, outputStream.toString());
    }

}