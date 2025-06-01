package devices.camera;

public class NightVision extends CameraDecorator {

    public NightVision(Camera wrapped) {
        super(wrapped);
    }

    @Override
    public void captureImage() {
        System.out.println(getName() + " is going to take a picture with enhanced Night Vision...");
        wrapped.captureImage();
    }

    @Override
    public void recordVideo() {
        System.out.println(getName() + " is going to record with enhanced Night Vision... spooky...");
        wrapped.recordVideo();
    }

    @Override
    public void turnOn() {
        wrapped.turnOn();
    }

    @Override
    public void turnOff() {
        wrapped.turnOff();
    }

}
