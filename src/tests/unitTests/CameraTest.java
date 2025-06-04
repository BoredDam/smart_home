package tests.unitTests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import devices.camera.*;
import devices.Device;

public class CameraTest {

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
    public void RecordVideoTest() {
        Camera c = new NightVision(new HDAudio(new BaseCamera("TestCamera"))); 
        c.recordVideo();
        String expectedOutput = "[TestCamera] Going to record with enhanced Night Vision... spooky..." + System.lineSeparator() + 
            "[TestCamera] Going to record with enhanced Audio..." + System.lineSeparator() + "[TestCamera] Recording..." + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());      
    }

    @Test
    public void CaptureImageTest() {
        Camera c = new ThermalVision(new BaseCamera("TestCamera2"));
        c.captureImage();
        String expectedOutput = "[TestCamera2] Going to take a picture with enhanced Thermal Vision..." + System.lineSeparator() +
            "[TestCamera2] Just took a picture." + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }
}
