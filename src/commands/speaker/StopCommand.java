package commands.speaker;

import devices.speaker.Speaker;

public class StopCommand extends SpeakerCommand {

    public StopCommand(Speaker speaker) {
        super(speaker);
    }
    
    @Override
    public void run() {
        speaker.stop();
    }
}
