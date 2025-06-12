package devices.speaker;
import devices.Device;
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
    public void pause() {
        wrapped.pause();
    }

    @Override
    public void play() {
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

    @Override
    public String getBaseType() {
        return "Speaker";
    } 
    
}
