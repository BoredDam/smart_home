package commands.speaker;

public class SetVolumeCommand extends SpeakerCommand {

    private final int level;

    public SetVolumeCommand(int level) {
        super();
        this.level = level;
    }

    @Override
    public void run() {
        speaker.setVolume(level);
    }
}
