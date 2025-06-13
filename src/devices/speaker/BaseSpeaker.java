package devices.speaker;

public class BaseSpeaker extends Speaker {

    SpeakerState spstate;
    int volume;

    public BaseSpeaker(String name) {
        super(name);
        spstate = StopState.getInstance();
        volume = 5;
    }

    @Override
    public void pause() {
        printHeader();
        spstate = spstate.pause();
    }

    @Override
    public void play() {
        printHeader();
        spstate = spstate.play();
    }

    @Override
    public void stop() {
        printHeader();
        spstate = spstate.stop();
    }

    @Override
    public void setVolume(int volume) {
        this.volume = volume;
        printHeader();
        System.out.println("Setting volume to " + volume);
    }

    @Override
    public void printInfos() {
        printHeader();
        System.out.print("Current volume level: " + volume + ". ");
        spstate.getInfo();
    }

    @Override
    public String getBaseType() {
        return "Speaker";
    } 
    
}
