package devices.camera;

public abstract class CameraDecorator implements Camera {

    protected Camera wrapped;

    public CameraDecorator(Camera wrapped) {
        this.wrapped = wrapped;
    }
    
    public abstract void captureImage();
    
    public abstract void recordVideo();


}
