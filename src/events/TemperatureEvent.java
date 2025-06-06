package events;

public abstract class TemperatureEvent extends Event {

    protected float measuredTemp;

    public TemperatureEvent(float temp) {
        super();
        measuredTemp = temp;
        System.out.println("A temperatureEvent was raised! Temperature: " + measuredTemp);
    }

}
