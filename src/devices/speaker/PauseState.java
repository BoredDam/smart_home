package devices.speaker;

public class PauseState implements SpeakerState {

    static PauseState instance;
    private PauseState() {}

    public static PauseState getInstance() {
        if(instance == null) {
            instance = new PauseState();
        } 
        return instance;
    }

    @Override
    public SpeakerState play() {
        System.out.println("Putting the music back on! ♫♪♪");
        return PlayState.getInstance();
    }

    @Override
    public SpeakerState pause() {
        System.out.println("Music is already paused.");
        return instance;
    }

    @Override
    public SpeakerState stop() {
        System.out.println("Music just got stopped...aight. *sob*");
        return StopState.getInstance();
    }
    
}
