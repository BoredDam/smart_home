package commands.cameraCommands;

public class CaptureImage extends CameraCommand {

    public CaptureImage() {
        super();
    }

    @Override
    public void run() {
        camera.captureImage();
    }
    
}
