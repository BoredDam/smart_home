package devices.airConditioner;

import devices.Device;

public class AirConditioner extends Device {
    
    private float targetTemp;

    public AirConditioner(String name){
        super(name);
    }

    public void setTargetTemp(float temp) {
        targetTemp = temp;
    }

    public float getTargetTemp() {
        return targetTemp;
    }

    @Override
    public String getBaseType() {
        return "AirConditioner";
    }
    
}
