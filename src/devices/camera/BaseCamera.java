package devices.camera;

/*
 * A class representing a normal camera device.
 */
public class BaseCamera extends Camera {

    public BaseCamera(String name) {
        super(name);
    }
    
    @Override
    public void captureImage() {
        printHeader();
        System.out.println("Just took a picture.");
    }

    @Override
    public void recordVideo() {
        printHeader();
        System.out.println("Recording...");
    }

    @Override
    public String getBaseType() {
        return "Camera";
    }

}
