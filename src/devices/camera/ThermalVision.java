package devices.camera;

/**
 * A class representing a thermal vision enhancement for a camera.
 * It affects the output of the captureImage and recordVideo methods by notifying the use of thermal vision.
 */
public class ThermalVision extends CameraDecorator {

    public ThermalVision(Camera wrapped) {
        super(wrapped);
    }

    @Override
    public void captureImage() {
        printHeader();
        System.out.println("Going to take a picture with enhanced Thermal Vision...");
        super.captureImage();
    }

    @Override
    public void recordVideo() {
        printHeader();
        System.out.println("Going to record with enhanced Thermal Vision...");
        super.recordVideo();
    }

}
