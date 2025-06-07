package commands.cameraCommands;

import commands.Command;
import devices.Device;
import devices.camera.Camera;

public abstract class CameraCommand implements Command {
    
    protected Camera camera;

    public CameraCommand() {}

    @Override
    public void setDevice(Device dev) {
        camera = (Camera) dev;
    }
}
