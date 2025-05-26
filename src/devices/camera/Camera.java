package devices.camera;
import devices.Device;

public abstract class Camera extends Device {

    public Camera(String name) {
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
