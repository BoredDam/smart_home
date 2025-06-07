package commands.speaker;

public class PauseCommand extends SpeakerCommand {

    public PauseCommand() {
        super();
    }
    
    @Override
    public void run() {
        speaker.pause();
    }
}
