package devices.speaker;

public interface SpeakerState {
    public SpeakerState play();
    public SpeakerState pause();
    public SpeakerState stop();
    public void getInfo();
}
