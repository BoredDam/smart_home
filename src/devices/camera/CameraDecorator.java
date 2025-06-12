package devices.camera;

import devices.Device;

public abstract class CameraDecorator extends Camera {

    private final Camera wrapped;

    public CameraDecorator(Camera wrapped) {
        super(wrapped.getName());
        this.wrapped = wrapped;
    }

    @Override
    public String getType() {
        String ret = this.getClass().getSimpleName();
        Device current = wrapped;
        while(current instanceof CameraDecorator wd) {
            ret = wd.getClass().getSimpleName() + ", " + ret;
            current = wd.wrapped; 
        }
        return ret + ", BaseCamera";
    }

    @Override
    public void captureImage() {
        wrapped.captureImage();
    }

    @Override
    public void recordVideo() {
        wrapped.recordVideo();
    }

    @Override
    public String getBaseType() {
        return "Camera";
    }
}
