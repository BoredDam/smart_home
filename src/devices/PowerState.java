package devices;

public interface PowerState {
    PowerState turnOn();
    PowerState turnOff();
    boolean isOn();
    boolean isOff();
}
