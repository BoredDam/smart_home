package devices.speaker;

/**
 * Interface representing the state of a speaker.
 * Defines methods for playing, pausing, stopping, and getting information about the speaker.
 * Each method returns a new state of the speaker.
 * @author Paolo Volpini
 * @author Damiano Trovato
 */
public interface SpeakerState {
    public SpeakerState play();
    public SpeakerState pause();
    public SpeakerState stop();
    public void getInfo();
}
