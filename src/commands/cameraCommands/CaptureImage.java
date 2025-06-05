package commands.cameraCommands;

import devices.camera.Camera;

public class CaptureImage extends CameraCommand {

    CaptureImage(Camera camera) {
        super(camera);
    }

    @Override
    public void run() {
        camera.captureImage();
    }
    
}
