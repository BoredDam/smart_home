package commands.speaker;

import commands.Command;
import devices.Device;
import devices.speaker.Speaker;

public abstract class SpeakerCommand implements Command {
    
    protected Speaker speaker;

    public SpeakerCommand() {
    }
    
    @Override
    public void setDevice(Device dev) {
        speaker = (Speaker) dev;
    }

    
}
