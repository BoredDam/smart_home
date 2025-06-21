package devices.camera;

import devices.ObservableDevice;

/**
 * Abstract class representing a camera device.
 * Every camera can capture images and record videos.
 */
public abstract class Camera extends ObservableDevice {
    public Camera(String name) {
        super(name);
    }
    @Override
    public void notifyObserver() {
        if(isOn() && controllerObserving != null) { 
            controllerObserving.update(this, "Intrusion");
            }
    }
    
    public abstract void captureImage();
    public abstract void recordVideo();

}
