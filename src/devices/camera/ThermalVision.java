package devices.camera;

public class ThermalVision extends BaseCameraDecorator {

    public ThermalVision(Camera wrapped) {
        super(wrapped);
    }

    @Override
    public void captureImage() {
        System.out.println(getName() + " is going to take a picture with enhanced Thermal Vision...");
        wrapped.captureImage();
    }

    @Override
    public void recordVideo() {
        System.out.println(getName() + " is going to record with enhanced Thermal Vision...");
        wrapped.recordVideo();
    }

}
