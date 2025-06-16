package devices.speaker;
import devices.Device;

/**
 * Abstract class representing a speaker device.
 * @author Paolo Volpini
 * @author Damiano Trovato
 */
public abstract class Speaker extends Device {
    public Speaker(String name) {
        super(name);
    }
    /**
     * Pauses the speaker.
     */
    abstract public void pause();
    /**
     * Plays music on the speaker.
     */
    abstract public void play();
    /**
     * Stops the speaker.
     */
    abstract public void stop();
    /**
     * Sets the volume of the speaker.
     * @param volume The volume level to set (0-100).
     */
    abstract public void setVolume(int volume);
    abstract public void printInfos();
}
