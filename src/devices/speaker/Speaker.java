package devices.speaker;

public interface Speaker {
    void pause();
    void play();
    void stop();
    void setVolume(int volume);
    void printInfos();
}
