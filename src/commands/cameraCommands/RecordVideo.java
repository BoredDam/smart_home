package commands.cameraCommands;

import devices.camera.Camera;

public class RecordVideo extends CameraCommand {

    public RecordVideo(Camera camera) {
        super(camera);
    }

    @Override
    public void run() {
        camera.recordVideo();
    }
    
}
