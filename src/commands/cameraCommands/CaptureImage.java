package commands.cameraCommands;

import devices.camera.Camera;

public class CaptureImage extends CameraCommand{

    CaptureImage(Camera device) {
        super(device);
    }

    @Override
    public void run() {
        device.captureImage();
    }
    
}
