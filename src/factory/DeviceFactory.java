package factory;

import devices.Device;
import devices.adapter.OldHeaterAdapter;
import devices.airConditioner.AirConditioner;
import devices.camera.BaseCamera;
import devices.door.Door;
import devices.light.Light;
import devices.speaker.BaseSpeaker;
import devices.thermostat.Thermostat;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class DeviceFactory {
    private final Map<String, Class<? extends Device>> classMap;      

    private static DeviceFactory instance;
    private DeviceFactory() {
        classMap = new HashMap<>();
        classMap.put("Door", Door.class);
        classMap.put("Camera", BaseCamera.class);
        classMap.put("Air Conditioner", AirConditioner.class);
        classMap.put("Light", Light.class);
        classMap.put("Speaker", BaseSpeaker.class);
        classMap.put("Thermostat",  Thermostat.class);
        classMap.put("OldHeater", OldHeaterAdapter.class);
    }
    
    public static DeviceFactory getInstance() {
        if(instance == null)
            instance = new DeviceFactory();
        return instance;
    }

    // it's supposed that a certain evaluation is given for the string type in order to have insensitive case matching
    public Device createDevice(String type, String name) {
        Class<? extends Device> cp = classMap.get(type);
        try {
            return (Device) cp.getConstructor(String.class).newInstance(name);
        }
        catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
    } 

}
