package commands.cameraCommands;

import commands.Command;
import devices.camera.Camera;

public abstract class CameraCommand implements Command {
    
    protected Camera camera;

    CameraCommand(Camera camera) {
        this.camera = camera;
    }

    @Override
    public abstract void run();

}
