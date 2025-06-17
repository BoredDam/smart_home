package devices.speaker;

/**
 * SpeakerState is the base interface for all the play-states subclasses, which are used inside the "speaker" device.
 * They are used to clarify about the (music?) playing state of the speaker, and also to encapsulate the behaviour
 * of the speaker, which changes in relationship to the playing-state.
 * 
 * It follows the logic behind the "State" design pattern by the GoF.
 * 
 * @author Paolo Volpini
 * @author Damiano Trovato
 */

public class PlayState implements SpeakerState {

    static PlayState instance;
    private PlayState() {}

    /**
     * Singleton pattern to ensure that there's only a single instance
     * of <code>PlayState</code>.
     * @return the only PlayState instance
     */
    public static PlayState getInstance() {
        if(instance == null) {
            instance = new PlayState();
        } 
        return instance;
    }

    @Override
    public SpeakerState play() {
        System.out.println("Music is already playing!");
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
