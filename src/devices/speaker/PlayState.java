package devices.speaker;

public class PlayState implements SpeakerState {

    static PlayState instance;
    private PlayState() {}

    public static PlayState getInstance() {
        if(instance == null) {
            instance = new PlayState();
        } 
        return instance;
    }

    @Override
    public SpeakerState play() {
        System.out.println("Music is already playing! ♫♪♪");
        return instance;
    }

    @Override
    public SpeakerState pause() {
        System.out.println("Music just got paused.");
        return PauseState.getInstance();
    }

    @Override
    public SpeakerState stop() {
        System.out.println("Music just got stopped...aight. *sob*");
        return StopState.getInstance();
    }
    @Override
    public void getInfo() {
        System.out.println("Currently playing.");
    }
}
