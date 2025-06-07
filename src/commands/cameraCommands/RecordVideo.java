package commands.cameraCommands;

public class RecordVideo extends CameraCommand {

    public RecordVideo() {
        super();
    }

    @Override
    public void run() {
        camera.recordVideo();
    }
    
}
