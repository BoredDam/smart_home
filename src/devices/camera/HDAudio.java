package devices.camera;

public class HDAudio extends CameraDecorator {
    
    public HDAudio(Camera wrapped) {
        super(wrapped);
    }

    @Override
    public void captureImage() {
        wrapped.captureImage();
    }

    @Override
    public void recordVideo() {
        System.out.println(/*getName() +*/ " is going to record with enhanced Audio...");
        wrapped.recordVideo();
    }

    @Override
    public void turnOn() {
        wrapped.turnOn();
    }

    @Override
    public void turnOff() {
        
    }

}
