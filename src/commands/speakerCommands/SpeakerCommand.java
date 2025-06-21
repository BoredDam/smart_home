package commands.speakerCommands;

import commands.Command;
import devices.Device;
import devices.speaker.Speaker;

public abstract class SpeakerCommand implements Command {
    
    protected Speaker speaker;

    /**
     * Base class for commands that operate on a Speaker device.
     */
    public SpeakerCommand() {
    }
    
    @Override
    public void setDevice(Device dev) {
        speaker = (Speaker) dev;
    }

    
}
