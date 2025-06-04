package devices.powerState;

/**
 * PowerState it's the base interface for all the the states subclasses.
 * It's used to clarify about the powering state.
 * 
 * @author Paolo Volpini
 * @author Damiano Trovato
 */
public interface PowerState {
    /**
     * 
     * @return
     */
    PowerState turnOn();
    PowerState turnOff();
    boolean isOn();
    boolean isOff();
}
