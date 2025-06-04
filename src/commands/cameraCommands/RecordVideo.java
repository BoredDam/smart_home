package commands.cameraCommands;

import devices.camera.Camera;

public class RecordVideo extends CameraCommand {

    RecordVideo(Camera device) {
        super(device);
    }

    @Override
    public void run() {
        device.recordVideo();
    }
    
}
