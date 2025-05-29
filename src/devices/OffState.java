package devices;

public class OffState implements PowerState {
    static OffState instance;
    private OffState() {}
    public static OffState getInstance() {
        if(instance == null) instance = new OffState();
        return instance;
    }

    @Override
    public PowerState turnOff() {
        System.out.println("Already off!");
        return instance;
    }

    @Override
    public PowerState turnOn() {
        System.out.println("Turned on!");
        return OnState.getInstance();
    }

    @Override
    public boolean isOn() {
        return false;
    }
    @Override
    public boolean isOff() {
        return true;
    }
}
