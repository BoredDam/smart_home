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

public class DecoratorFactory {
    private static record DecoratorEntry(String name, Class<? extends Device> expectedType, Class<? extends Device> decoratorType) {}
    private final List<DecoratorEntry> decoratorBuilders = new ArrayList<>();
    private static DecoratorFactory instance;

    private DecoratorFactory() {
        decoratorBuilders.add(new DecoratorEntry("hdaudio", Camera.class, HDAudio.class));
        decoratorBuilders.add(new DecoratorEntry("nightvision", Camera.class, NightVision.class));
        decoratorBuilders.add(new DecoratorEntry("thermalvision", Camera.class, ThermalVision.class));
        decoratorBuilders.add(new DecoratorEntry("amazonmusic", Speaker.class, AmazonMusicApp.class));
        decoratorBuilders.add(new DecoratorEntry("youtubemusic", Speaker.class, YoutubeMusicApp.class));
        decoratorBuilders.add(new DecoratorEntry("spotify", Speaker.class, SpotifyApp.class));
    }

    public static DecoratorFactory getInstance() {
        if (instance == null) {
            instance = new DecoratorFactory();
        }
            
        return instance;
    }

    public Device addFunctionality(Device dev, String func) {
        String functionality = func.toLowerCase();
        
        if (dev.getType().contains(functionality)) {
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
