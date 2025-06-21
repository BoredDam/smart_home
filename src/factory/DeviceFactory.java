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
import java.util.stream.Collectors;

/**
 * the DeviceFactory class is based on the "Factory Method" design pattern by the GoF.
 * It offers a simple way to create instances of classes which implements 
 * the Device interface (not the decorators though).
 * It is also a Singleton.
 * 
 * @author Paolo Volpini
 * @author Damiano Trovato
 */
public class DeviceFactory {
    private final Map<String, Class<? extends Device>> classMap;      

    private static DeviceFactory instance;

    
    /**
     * @hidden Constructor of the devices factory. For every device type of the system, it has to put a new record in the classMap. 
     * Every record has to be like: <code>(String lowercase_thisdevice_name, Class ThisDevice.class)</code>. It makes use
     * of the Reflection API.
     */
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

    /**
     * @return a String containing every device type that the DeviceFactory (and the whole system) supports.
     */
    public String availableTypesToString() {
        return classMap.keySet().stream().collect(Collectors.joining(", "));
    }

    /**
     * Makes an instance of a given device-type class.
     * @param type the class-name of the new instance. It's not case-sensitive.
     * @param name the name of the new instance.
     * @return an instance of a class that implements Device of the given type.
     */
    public Device createDevice(String type, String name) {
        
        Class<? extends Device> cp = classMap.get(type.toLowerCase());
        try {
            return (Device) cp.getConstructor(String.class).newInstance(name);
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException 
                | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
    } 

    /**
     * @param typeName a class-name.
     * @return <code>true</code> if a class with the specified class-name
     *         is supported by the DeviceFactory. <code>false</code> otherwise.
     */
    public boolean lookFor(String typeName) {
        return classMap.containsKey(typeName.toLowerCase());
    }
}