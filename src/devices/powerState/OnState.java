package devices.powerState;

public class OnState implements PowerState {
    static OnState instance;
    private OnState() {}

    /**
     * This method checks if an instance of <code>OnState</code> was already instantiated, and if not, it will instantiate it.
     * 
     * Then, whether it was just instantiated or not, it will return the instance. 
     * This is a clear implementation of the Singleton Design Pattern by the GoF.
     * 
     * @return the OnState instance;
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
}
