package environment;

import devices.Device;
import devices.adapter.OldHeaterAdapter;
import devices.airConditioner.AirConditioner;
import devices.camera.Camera;
import devices.door.Door;
import devices.thermostat.Thermostat;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * the Environment class simulates a virtual environment for the SmartHomeController devices
 * to react to. It offers a way to trigger observable devices, such cameras, thermostats or doors.
 * 
 * @author Paolo Volpini
 * @author Damiano Trovato
 */
public class Environment {
    
    private float temp; // temperature
    private final float influenceFactor; // It is a constant that establishes how much the devices influence the temperature
    private final List<Device> device_list; 

    /**
     * @param device_list HAS to be an unmodifiable list of the controller's list of devices.
     */
    public Environment(List<Device> device_list) {
        // Note: the device_list that is passed in the class is supposed
        // to be an UNMODIFIABLE list of the controller's list of devices for safety issues.
        // If the device_list is different than the one on the controller, the class makes ABSOLUTELY NO SENSE.
        this.device_list = device_list;
        temp = 20.0f;
        influenceFactor = 0.2f;
    }

    /**
     * Calculates temperature based on current temperature and air conditioner turned on, 
     * then updates every thermostat in the device list.
     */
    public void calculateTemperature() {
        float avgTarget = 0.0f;
        int count = 0;

        for(Device dev: device_list) {
            if(dev instanceof AirConditioner ac && ac.isOn()) {
                avgTarget += ac.getTargetTemp();
                count++;
            }
        }
        
        if(count > 0) {
            avgTarget /= count;
            this.temp = this.temp + (avgTarget - this.temp) * influenceFactor;
        }

        for(Device dev: device_list) {
            if(dev instanceof OldHeaterAdapter od && od.isOn()) { // if an OldHeater is present, it will just raise the temperature by 0.5 degrees
                this.temp += 0.5f;
            }
        }
        updateAllThermostats();
    }

    /**
     * Updates every thermostat in the list of device.
     */
    private void updateAllThermostats() {
        for(Device dev: device_list) {
            if(dev instanceof Thermostat t && t.isOn()) {
                t.measureTemperature(temp);
            }
        }
    }
    
    /**
     * Performs an action to a given door.
     * @param devName is the door name.
     * @param open if it's <code>true</code>, the door is opened. if it's <code>false</code>, the door is closed.
     */
    public void actionOnDoor(String devName, boolean open) {
        Door door = (Door) device_list.stream()
            .filter(dev -> dev.getName().equals(devName) && dev instanceof Door)
            .findFirst()
            .orElse(null);

        if (door == null) {
            System.out.println("[Environment] Door not found. Maybe the name is wrong?");
            return;
        }

        if(door.isOn()) {
            if (open) {
              door.open();
            } else {
                door.close();
            }
        }
        else {
            System.out.println("[Environment] Door " + devName + " is currently off, so no action can be performed.");
        }
    } 
    
    /**
     * Performs an action to a random door of the system.
     * @param open if it's <code>true</code>, the door is opened. if it's <code>false</code>, the door is closed.
     */
    public void actionOnRandomDoor(boolean open) {
        List<Door> tempList = device_list.stream()
            .filter(dev -> (dev instanceof Door))
            .map(dev -> (Door) dev)
            .collect(Collectors.toList());
        if(tempList.isEmpty()) {
            return;
        }

        Random rand = new Random();
        int randomIndex = rand.nextInt(tempList.size());
        Door randomDoor = tempList.get(randomIndex);
        if(randomDoor.isOn()) {
            if(open) {
                randomDoor.open();
            }
            else {
                randomDoor.close();
            }
        }
        else {
            System.out.println("[Environment] Door " + randomDoor.getName() + " is currently off, so no action can be performed.");
        }
    }   

    /**
     * Makes a random camera in the system detect a presence.
     */
    public void randomCameraPresenceDetection() {
        List<Camera> cameras = device_list.stream()
                .filter(dev -> (dev instanceof Camera))
                .map(dev -> (Camera) dev)
                .collect(Collectors.toList());
        if(!cameras.isEmpty()) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(cameras.size());
            Camera randomCamera = cameras.get(randomIndex);
            randomCamera.notifyObserver();
        }
    }

    /**
     * Makes a given camera in the system detect a presence.
     * @param devName the name of the camera that will detect a presence.
     */
    public void cameraPresenceDetection(String devName) {
        Camera camera = (Camera) device_list.stream()
            .filter(dev -> (dev.getName().equals(devName) && dev instanceof Camera))
            .findFirst()
            .orElse(null);

        if(camera != null) {
            camera.notifyObserver();
        } else {
            System.out.println("[Environment] Camera " + devName + "not found. Maybe the name is wrong?");
        }
    }
}
