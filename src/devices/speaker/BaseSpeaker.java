package devices.speaker;

import devices.Device;

public class BaseSpeaker extends Device implements Speaker {

    SpeakerState spstate;
    int volume;

    public BaseSpeaker(String name) {
        super(name);
        spstate = StopState.getInstance();
        volume = 5;
    }

    @Override
    public void pause() {
        spstate.pause();
    }

    @Override
    public void play() {
        spstate.play();
    }

    @Override
    public void stop() {
        spstate.stop();
    }

    @Override
    public void setVolume(int volume) {
        this.volume = volume;
        System.out.println("["+ getName() + "] Setting volume to" + volume);
    }

    @Override
    public void printInfos() {
        System.out.println("["+ getName() + "] I'll print the downloaded apps list.");
    } 
    
}
