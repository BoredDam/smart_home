package devices.speaker;
import devices.Device;

public abstract class Speaker extends Device {
    public Speaker(String name) {
        super(name);
    }
    abstract public void pause();
    abstract public void play();
    abstract public void stop();
    abstract public void setVolume(int volume);
    abstract public void printInfos();
}
