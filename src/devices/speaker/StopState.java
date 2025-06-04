package devices.speaker;

public class StopState implements SpeakerState {

    static StopState instance;
    private StopState() {}

    public static StopState getInstance() {
        if(instance == null) {
            instance = new StopState();
        } 
        return instance;
    }

    @Override
    public SpeakerState play() {
        System.out.println("Music is going to play!");
        return PlayState.getInstance();
    }

    @Override
    public SpeakerState pause() {
        System.out.println("Music was not playing...");
        return instance;
    }

    @Override
    public SpeakerState stop() {
        System.out.println("Music was not playing...");
        return instance;
    }
    
}
