package commands.cameraCommands;

public class RecordVideo extends CameraCommand {

    /**
     * Command to record a video from the camera.
     */
    public RecordVideo() {
        super();
    }

    @Override
    public void run() {
        camera.recordVideo();
    }
    
}
