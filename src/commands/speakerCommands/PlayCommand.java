package commands.speakerCommands;

public class PlayCommand extends SpeakerCommand {

    /**
     * Command to play the speaker.
     */
    public PlayCommand() {
        super();
    }
    
    @Override
    public void run() {
        speaker.play();
    }
}
