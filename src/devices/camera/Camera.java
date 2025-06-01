package devices.camera;

import devices.Device;

public abstract class Camera extends Device {

    public Camera(String name) {
        super(name);
    }
    
    public abstract void captureImage();
    public abstract void recordVideo();
}
