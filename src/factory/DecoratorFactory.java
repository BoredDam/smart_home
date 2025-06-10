package factory;

import devices.Device;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;


public class DecoratorFactory {
    private final Map<String, Class<? extends Device>> classMap;
    private static DecoratorFactory instance;

    private DecoratorFactory() {
        classMap = new HashMap<>();
    }

    public static DecoratorFactory getInstance() {
        if(instance == null) 
            instance = new DecoratorFactory();
        return instance;
    }

    public Device addFunctionality(Device dev, String functionality) {
        Class<? extends Device> decorator = classMap.get(functionality);
        Device ret = null;
        if(decorator == null) {
            System.out.println("Device given does not support that functionality!");
        }
        else {
            try {
                ret = decorator.getConstructor(Device.class).newInstance(dev);
            } 
            catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | NoSuchMethodException | InvocationTargetException e) {
                System.out.println("Selected functionality cannot be added to the device");
            }
        }
        return ret;
    }
}
