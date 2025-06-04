package devices.camera;

public class NightVision extends CameraDecorator {

    public NightVision(Camera wrapped) {
        super(wrapped);
    }

    @Override
    public void captureImage() {
        printHeader();
        System.out.println("Going to take a picture with enhanced Night Vision...");
        super.captureImage();
    }

    @Override
    public void recordVideo() {
        printHeader();
        System.out.println("Going to record with enhanced Night Vision... spooky...");
        super.recordVideo();
    }
}
