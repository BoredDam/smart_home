package commands.speaker;

public class StopCommand extends SpeakerCommand {

    public StopCommand() {
        super();
    }
    
    @Override
    public void run() {
        speaker.stop();
    }
}
