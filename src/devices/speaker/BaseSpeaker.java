package devices.speaker;

/**
 * Represents a base speaker device that can be controlled to play, pause, stop,
 * and adjust the volume. State is managed using the State design pattern.
 * @author Paolo Volpini
 * @author Damiano Trovato
 */
public class BaseSpeaker extends Speaker {

    private SpeakerState spstate;
    private int volume;

    public BaseSpeaker(String name) {
        super(name);
        spstate = StopState.getInstance();
        volume = 5;
    }

    @Override
    public void turnOff() {
        super.turnOff();
        stop();
    }

    @Override
    public void pause() {
        printHeader();
        spstate = spstate.pause();
        printInfos();
    }

    @Override
    public void play() {
        printHeader();
        spstate = spstate.play();
        printInfos();
    }

    @Override
    public void stop() {
        printHeader();
        spstate = spstate.stop();
        printInfos();
    }

    @Override
    public String getState() {
        return super.getState() + ", " + spstate.getState();
    }

    @Override
    public String getSpeakerState() {
        return spstate.getState();
    }
    
    @Override
    public void setVolume(int volume) {
        printHeader();
        if(volume < 0 || volume > 100) {
            System.out.println("Value must be between 0 and 100. Setting to default value 5.");
            this.volume = 5;
        } else {
            this.volume = volume;
        }
        System.out.println("Setting volume to " + volume);
        printInfos();
    }

    @Override
    public void printInfos() {
        printHeader();
        System.out.print("Current volume level: " + volume + ". ");
        spstate.getInfo();
    }

}
