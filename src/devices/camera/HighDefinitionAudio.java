package devices.camera;

public class HighDefinitionAudio extends BaseCameraDecorator {
    
    public HighDefinitionAudio(Camera wrapped) {
        super(wrapped);
    }

    @Override
    public void captureImage() {
        wrapped.captureImage();
    }

    @Override
    public void recordVideo() {
        System.out.println(getName() + " is going to record with enhanced Audio...");
        wrapped.recordVideo();
    }

}
