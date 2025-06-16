package commands.speakerCommands;

public class StopCommand extends SpeakerCommand {

    /**
     * Command to stop the speaker.
     */
    public StopCommand() {
        super();
    }
    
    @Override
    public void run() {
        speaker.stop();
    }
}
