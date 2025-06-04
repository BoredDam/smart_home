package devices.camera;

public abstract class CameraDecorator extends Camera {

    private Camera wrapped;

    public CameraDecorator(Camera wrapped) {
        super(wrapped.getName());
        this.wrapped = wrapped;
    }
    @Override
    public void captureImage() {
        wrapped.captureImage();
    }
    @Override
    public void recordVideo() {
        wrapped.recordVideo();
    }


}
