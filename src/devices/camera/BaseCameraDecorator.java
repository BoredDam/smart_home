package devices.camera;

public abstract class BaseCameraDecorator extends Camera{

    protected Camera wrapped;

    public BaseCameraDecorator(Camera wrapped) {
        super(wrapped.getName());
        this.wrapped = wrapped;
    }
    
    public abstract void captureImage();
    
    public abstract void recordVideo();


}
