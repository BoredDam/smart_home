package commands.speaker;

import devices.speaker.Speaker;

public class PauseCommand extends SpeakerCommand {

    public PauseCommand(Speaker speaker) {
        super(speaker);
    }
    
    @Override
    public void run() {
        speaker.pause();
    }
}
