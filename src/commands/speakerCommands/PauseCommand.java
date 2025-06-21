package commands.speakerCommands;

public class PauseCommand extends SpeakerCommand {

    /**
     * Command to pause the speaker.
     */
    public PauseCommand() {
        super();
    }
    
    @Override
    public void run() {
        speaker.pause();
    }
}
