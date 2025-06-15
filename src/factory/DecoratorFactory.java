package factory;

import devices.Device;
import devices.camera.Camera;
import devices.camera.HDAudio;
import devices.camera.NightVision;
import devices.camera.ThermalVision;
import devices.speaker.AmazonMusicApp;
import devices.speaker.Speaker;
import devices.speaker.SpotifyApp;
import devices.speaker.YoutubeMusicApp;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * the DecoratorFactory class is based on the "Factory Method" design pattern by the GoF.
 * It offers a simple way to create instances of "Decorator"-classes, and add them to given devices.
 * It is also a Singleton.
 * 
 * @author Paolo Volpini
 * @author Damiano Trovato
 */

public class DecoratorFactory {
    private static record DecoratorEntry(String name, Class<? extends Device> expectedType, Class<? extends Device> decoratorType) {}
    private final List<DecoratorEntry> decoratorBuilders = new ArrayList<>();
    private static DecoratorFactory instance;
    /**
     * @hidden Constructor of the decorator factory. For every device type of the system, it has to put a new record in the classMap. 
     * Every record has to be like: 
     * <code>(String lowercase_thisdecorator_name, Class ThisDecorator.class, Class ExpectedDeviice.class)</code>.
     * It makes use of the Reflection API.
     */
    private DecoratorFactory() {

        decoratorBuilders.add(new DecoratorEntry("hdaudio", Camera.class, HDAudio.class));
        decoratorBuilders.add(new DecoratorEntry("nightvision", Camera.class, NightVision.class));
        decoratorBuilders.add(new DecoratorEntry("thermalvision", Camera.class, ThermalVision.class));
        decoratorBuilders.add(new DecoratorEntry("amazonmusic", Speaker.class, AmazonMusicApp.class));
        decoratorBuilders.add(new DecoratorEntry("youtubemusic", Speaker.class, YoutubeMusicApp.class));
        decoratorBuilders.add(new DecoratorEntry("spotify", Speaker.class, SpotifyApp.class));
    }

    /**
     * Singleton pattern to ensure that there's only a single instance
     * of <code>DecoratorFactory</code>.
     * @return the only DecoratorFactory instance
     */
    public static DecoratorFactory getInstance() {

        if (instance == null) {
            instance = new DecoratorFactory();
        }    
        return instance;
    }

    /**
     * @param prepend the type of a class that implements Device.
     * @return a string with every decorator supported by the specified devices.
     */
    public String availableDecsToString(String prepend) {

        return decoratorBuilders.stream()
                .map(rec -> prepend + rec.name + " for device of type " + rec.expectedType.getSimpleName().toLowerCase())
                .collect(Collectors.joining("\n"));
    }

    /**
     * Adds a functionality to a device.
     * @param dev the device instance to add the functionality to.
     * @param func the functionality to add to the device.
     * @return the instance of the decorated-device.
     */
    public Device addFunctionality(Device dev, String func) {

        String functionality = func.toLowerCase();
        
        if (dev.getType().toLowerCase().contains(functionality)) {
            System.out.println("[DecoratorFactory] Device already has that functionality!");
            return dev;
        }

        DecoratorEntry record = decoratorBuilders.stream().filter((rec) -> (rec.name.equals(functionality))).findFirst().orElse(null);
        if (record == null) {
            System.out.println("[DecoratorFactory] Functionality not found!");
            return null;
        }

        Class<? extends Device> decorator = record.decoratorType;
        Device ret = null;
        if (decorator == null) {
            System.out.println("Device given does not support that functionality!");
        } else {
            try {
                ret = decorator.getConstructor(record.expectedType).newInstance(dev);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | NoSuchMethodException | InvocationTargetException e) {
                System.out.println("Selected functionality cannot be added to the device");
            }
        }
        return ret;
    }

}
