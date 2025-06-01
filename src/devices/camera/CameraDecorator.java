package devices.camera;

public abstract class CameraDecorator extends Camera {

    protected Camera wrapped;

    public CameraDecorator(Camera wrapped) {
        super(wrapped.getName());
        this.wrapped = wrapped;
    }
    
    public abstract void captureImage();
    public abstract void recordVideo();


}
