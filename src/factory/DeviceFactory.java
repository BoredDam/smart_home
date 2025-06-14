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
        classMap.put("door", Door.class);
        classMap.put("camera", BaseCamera.class);
        classMap.put("air conditioner", AirConditioner.class);
        classMap.put("light", Light.class);
        classMap.put("speaker", BaseSpeaker.class);
        classMap.put("thermostat",  Thermostat.class);
        classMap.put("old heater", OldHeaterAdapter.class);
    }
    
    /**
     * Singleton pattern to ensure that there's only a single instance
     * of <code>DeviceFactory</code>.
     * @return the only DeviceFactory instance
     */
    public static DeviceFactory getInstance() {
        if (instance == null) {
            instance = new DeviceFactory();
        }
        return instance;
    }

    // it's supposed that a certain evaluation is given for the string type in order to have insensitive case matching
    public Device createDevice(String type, String name) {
        
        Class<? extends Device> cp = classMap.get(type.toLowerCase());
        try {
            return (Device) cp.getConstructor(String.class).newInstance(name);
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException 
                | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
    } 

    public boolean lookFor(String typeName) {
        return classMap.containsKey(typeName.toLowerCase());
    }
}
