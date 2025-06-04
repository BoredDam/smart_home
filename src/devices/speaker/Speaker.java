package devices.speaker;
import devices.Device;

public abstract class Speaker extends Device {
    public Speaker(String name) {
        super(name);
    }
    abstract void pause();
    abstract void play();
    abstract void stop();
    abstract void setVolume(int volume);
    abstract void printInfos();
}
