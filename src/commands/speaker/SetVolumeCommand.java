package commands.speaker;

import devices.speaker.Speaker;

public class SetVolumeCommand extends SpeakerCommand {

    private final int level;

    public SetVolumeCommand(Speaker speaker, int level) {
        super(speaker);
        this.level = level;
    }

    @Override
    public void run() {
        speaker.setVolume(level);
    }
}
