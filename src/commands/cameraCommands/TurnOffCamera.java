package commands.cameraCommands;

import devices.camera.Camera;

public class TurnOffCamera extends CameraCommand{

    TurnOffCamera(Camera device) {
        super(device);
    }

    @Override
    public void execute() {
        device.turnOff();
    }
    
}
