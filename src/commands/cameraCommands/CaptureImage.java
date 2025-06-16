package commands.cameraCommands;
/**
 * Command to capture an image from the camera.
 * @author Paolo Volpini
 * @author Damiano Trovato
*/
public class CaptureImage extends CameraCommand {

    
    public CaptureImage() {
        super();
    }

    @Override
    public void run() {
        camera.captureImage();
    }
    
}
