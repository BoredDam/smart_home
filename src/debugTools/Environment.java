package debugTools;

import devices.Device;
import devices.adapter.OldHeaterAdapter;
import devices.airConditioner.AirConditioner;
import devices.camera.Camera;
import devices.door.Door;
import devices.thermostat.Thermostat;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Environment {
    
    private float temp;
    private final float influenceFactor;
    private final List<Device> device_list; 

    public Environment(List<Device> device_list) {
        // Note: the device_list that is passed in the class is supposed
        // to be an UNMODIFIABLE list of the controller's list of devices.
        // If the device_list is different, the class makes ABSOLUTELY NO SENSE.
        this.device_list = device_list;
        temp = 20.0f;
        influenceFactor = 0.2f;
    }

    // calculates temperature based on current temperature and air conditioner turned on
    // if an OldHeater is present, it will just raise the temperature by 0.5 degrees
    public void calculateTemperature() {
        float avgTarget = 0.0f;
        int count = 0;
        for(Device dev: device_list) {
            if(dev instanceof AirConditioner ac) {
                if(ac.isOn()) {
                    avgTarget = ac.getTargetTemp();
                    count++;
                }
            }
        }
        if(count > 0) {
            avgTarget /= count;
            this.temp = this.temp + (avgTarget - this.temp) * influenceFactor;
        }
        for(Device dev: device_list) {
            if(dev instanceof OldHeaterAdapter) {
                temp += 0.5f;
            }
        }
        updateAllThermostats();
    }

    private void updateAllThermostats() {
        for(Device dev: device_list) {
            if(dev instanceof Thermostat t) {
                t.measureTemperature(temp);
            }
        }
    }

    // Note: openRandomDoor() may be substituted if we 
    // rather want the user to select a specific door
    // Like, I don't know, I got mixed feeling about this
    public void openRandomDoor() {
        List<Door> tempList = device_list.stream().filter(dev -> (dev instanceof Door))
                .map(dev -> (Door) dev).collect(Collectors.toList());
        if(!tempList.isEmpty()) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(tempList.size());
            Door randomDoor = tempList.get(randomIndex);
            randomDoor.open();
        }
    }
    // If we don't like the random door opening, use the method below
    public void openDoor(String devName) {
        // note: the check "dev instanceof Door" is needed to avoid ClassCastExceptions
        // the stream does not work without it, and i don't want any try catch statement in here
        Door door = (Door) device_list.stream().filter(dev -> dev.getName().equals(devName) && dev instanceof Door).findFirst().orElse(null);
        if(door != null) {
            door.open();
        }
        else {
            System.out.println("[Environment] Door not found. Maybe the name is wrong?");
        }
    }

    // we do the same thing as above, but for calling notifyObserver() on cameras
    public void randomCameraPresenceDetection() {
        List<Camera> cameras = device_list.stream()
                .filter(dev -> (dev instanceof Camera))
                .map(dev -> (Camera) dev)
                .collect(Collectors.toList());
        if(!cameras.isEmpty()) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(cameras.size());
            Camera randomCamera =  cameras.get(randomIndex);
            randomCamera.notifyObserver();
        }
    }

    // again, if we don't like random selection, use the method below
    public void cameraPresenceDetection(String devName) {
        Camera camera = (Camera) device_list.stream().filter(dev -> (dev.getName().equals(devName) && dev instanceof Camera)).findFirst().orElse(null);
        if(camera != null) {
            camera.notifyObserver();
        } else {
            System.out.println("[Environment] Camera not found. Maybe the name is wrong?");
        }
    }
}
