package devices.speaker;
import devices.Device;
/**
 * Decorator for speaker devices.
 * @author Paolo Volpini
 * @author Damiano Trovato
 */
public abstract class SpeakerAppDecorator extends Speaker {

    private final Speaker wrapped;

    public SpeakerAppDecorator(Speaker wrapped) {
        super(wrapped.getName());
        this.wrapped = wrapped;
    }

    @Override
    public String getType() {
        String ret = this.getClass().getSimpleName();
        Device current = wrapped;
        while(current instanceof SpeakerAppDecorator wd) {
            ret = ret + ", " + wd.getClass().getSimpleName();
            current = wd.wrapped; 
        }
        return ret + ", BaseSpeaker";
    }

    @Override
    public void turnOff() {
        super.turnOff();
        stop();
    }
    @Override
    public void pause() {
        wrapped.pause();
    }
    @Override
    public String getSpeakerState() {
        return wrapped.getSpeakerState();
    }
    
    @Override
    public String getState() {
        return (isOn() ? "ON" : "OFF") + ", " + getSpeakerState();
    }
    
    @Override
    public void play() {
        printAddInfos();
        wrapped.play();
    }

    @Override
    public void stop() {
        wrapped.stop();
    }

    @Override
    public void setVolume(int volume) {
        wrapped.setVolume(volume);
    }

    @Override
    public void printInfos() {
        wrapped.printInfos();
    }

    public abstract void printAddInfos();

    @Override
    public String getBaseType() {
        return "Speaker";
    } 
    
}
