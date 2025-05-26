package commands.cameraCommands;

import devices.camera.Camera;

public class TurnOnCamera extends CameraCommand{

    TurnOnCamera(Camera device) {
        super(device);
    }

    @Override
    public void execute() {
        device.turnOn();
    }
    
}
