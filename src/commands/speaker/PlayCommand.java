package commands.speaker;

public class PlayCommand extends SpeakerCommand {

    public PlayCommand() {
        super();
    }
    
    @Override
    public void run() {
        speaker.play();
    }
}
