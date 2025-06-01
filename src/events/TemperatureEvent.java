package events;

public abstract class TemperatureEvent implements Event {

    protected float measuredTemp;

    public TemperatureEvent(float temp) {
        super();
        measuredTemp = temp;
        System.out.println("A temperatureEvent was raised! Temperature: " + measuredTemp);
    }

}
