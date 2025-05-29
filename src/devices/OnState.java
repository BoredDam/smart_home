package devices;

public class OnState implements PowerState {
    static OnState instance;
    private OnState() {}
    public static OnState getInstance() {
        if(instance == null) instance = new OnState();
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
