package commands.cameraCommands;

import commands.Command;
import devices.camera.Camera;

public abstract class CameraCommand implements Command {
    
    protected Camera device;

    CameraCommand(Camera device) {
        this.device = device;
    }

    @Override
    public abstract void run();

}
