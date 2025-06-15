package devices.camera;

import devices.ObservableDevice;

public abstract class Camera extends ObservableDevice {
    public Camera(String name) {
        super(name);
    }
    @Override
    public void notifyObserver() {
        if(isOn() && controllerObserving != null) controllerObserving.update(this, "Intrusion");
    }
    public abstract void captureImage();
    public abstract void recordVideo();

}
