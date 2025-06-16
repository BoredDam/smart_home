package commands.speakerCommands;

public class SetVolumeCommand extends SpeakerCommand {

    private final int level;

    /**
     * Command to set the volume of the speaker.
     * @param level The new volume level to set (0-100)
     */
    public SetVolumeCommand(int level) {
        super();
        this.level = level;
    }

    @Override
    public void run() {
        speaker.setVolume(level);
    }
}
