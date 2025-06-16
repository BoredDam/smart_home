package devices.camera;

/**
 * A class representing a high-definition audio enhancement for a camera.
 * It affects the output of the recordVideo method.
 */
public class HDAudio extends CameraDecorator {
    
    public HDAudio(Camera wrapped) {
        super(wrapped);
    }

    @Override
    public void captureImage() {
        super.captureImage();
    }

    @Override
    public void recordVideo() {
        printHeader();
        System.out.println("Going to record with enhanced Audio...");
        super.recordVideo();
    }

}
