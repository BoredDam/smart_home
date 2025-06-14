package devices.airConditioner;

import devices.Device;

public class AirConditioner extends Device {
    
    private float targetTemp;

    public AirConditioner(String name){
        super(name);
    }

    public void setTargetTemp(float temp) {
        printHeader();
        if (temp < 10 || temp > 50) {
            printHeader();
            System.out.println("Target temperature must be between 10 and 50. Setting to default value 23.");
            targetTemp = 23; // yea, 23 is ok in an air conditioner. Or is it...
        }
        else 
            targetTemp = temp;
        System.out.println("Target temperature set to: " + targetTemp + " °C");
    }

    public float getTargetTemp() {
        return targetTemp;
    }

    @Override
    public String getBaseType() {
        return "AirConditioner";
    }
    
}
