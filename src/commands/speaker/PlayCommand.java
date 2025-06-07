package commands.speaker;

import devices.speaker.Speaker;

public class PlayCommand extends SpeakerCommand {

    public PlayCommand(Speaker speaker) {
        super();
    }
    
    @Override
    public void run() {
        speaker.play();
    }
}
