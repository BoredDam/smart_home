package devices.camera;

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
