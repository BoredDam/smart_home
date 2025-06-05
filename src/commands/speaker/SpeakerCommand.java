package commands.speaker;

import devices.speaker.Speaker;
import commands.Command;

public abstract class SpeakerCommand implements Command {
    
    protected Speaker speaker;

    public SpeakerCommand(Speaker speaker) {
        this.speaker = speaker;
    }
    public void setDevice(Speaker speaker) {
        this.speaker = speaker;
    }

    public abstract void run();
}
