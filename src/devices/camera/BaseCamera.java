package devices.camera;

import devices.Device;

public class BaseCamera extends Device implements Camera {

    public BaseCamera(String name) {
        super(name);
    }

    public void turnOn() {

    }

    public void turnOff() {

    }

    public void captureImage() {
        System.out.println(getName() + " just took a picture.");
    }

    public void recordVideo() {
        System.out.println(getName() + " is recording...");
    }
    
}
