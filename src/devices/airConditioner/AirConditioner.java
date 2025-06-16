package devices.airConditioner;

import devices.Device;

/**
 * Represents an air conditioner device.
 * An air conditioner can be set to a target temperature.
 * When turned on, it will affect the environment temperature.
 */
public class AirConditioner extends Device {
    
    private float targetTemp = 23;

    public AirConditioner(String name){
        super(name);
    }

    /**
     * Sets the target temperature of the air conditioner.
     * @param targetTemp the new target temperature to set
     */
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

    /**
     * Gets the target temperature of the air conditioner.
     * @return the current target temperature
     */
    public float getTargetTemp() {
        return targetTemp;
    }

    @Override
    public String getBaseType() {
        return "AirConditioner";
    }
    
}
