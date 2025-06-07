package devices.powerState;

import commands.Command;

/**
 * <code> PowerState </code> it's the base interface for all the the states subclasses.
 * It's used to clarify about the powering state of the objects that it's composed into.
 * 
 * It follows the logic behind the "State" design pattern by the GoF.
 * 
 * @author Paolo Volpini
 * @author Damiano Trovato
 */

public interface PowerState {
    /**
     * This methods shifts the PowerState to <code>OnState</code>, then return the PowerState.
     * @return an instance of a <code>OnState</code>.
     */
    PowerState turnOn();

    /**
     * This methods shifts the PowerState to <code>OffState</code>, then return the PowerState.
     * @return an instance of a <code>OffState</code>.
     */
    PowerState turnOff();

    /**
     * This method checks if the PowerState is <code>OnState</code>.
     * @return <code>true</code> if the PowerState is <code>OnState</code>; <code>false</code> otherwise.
     */
    boolean isOn();
    
    /**
     * This method checks if the PowerState is <code>OffState</code>.
     * @return <code>true</code> if the PowerState is <code>OffState</code>; <code>false</code> otherwise.
     */
    boolean isOff();

    void runCommand(Command cmd);
}
