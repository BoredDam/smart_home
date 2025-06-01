package events;

public class HighTemperatureEvent extends TemperatureEvent {

    public HighTemperatureEvent(float temp) {
        super(temp);
        //this.action = new TurnOnCooling();
    }

}
