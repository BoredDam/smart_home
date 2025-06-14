package devices.camera;

import devices.ObservableDevice;

public abstract class Camera extends ObservableDevice {
    public Camera(String name) {
        super(name);
    }
    
    public abstract void captureImage();
    public abstract void recordVideo();

}
