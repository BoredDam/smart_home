package devices.powerState;

import commands.Command;

public class OnState implements PowerState {
    private static OnState instance;
    private OnState() {}

    /**
     * Singleton pattern to ensure that there's only a single instance
     * of <code>OnState</code>.
     * @return the only OnState instance
     */
    public static OnState getInstance() {
        if(instance == null) {
            instance = new OnState();
        } 
        return instance;
    }

    @Override
    public PowerState turnOn() {
        System.out.println("Already on!");
        return instance;
    }

    @Override
    public PowerState turnOff() {
        System.out.println("Turned off!");
        return OffState.getInstance();
    }

    @Override
    public boolean isOn() {
        return true;
    }

    @Override
    public boolean isOff() {
        return false;
    }

    @Override
    public void runCommand(Command cmd) {
        cmd.run();
    }
}
