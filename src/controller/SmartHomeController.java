package controller;

import devices.Device;
import events.Event;
import java.util.ArrayList;
import java.util.List;


public class SmartHomeController implements Observer {
    
    private List<Device> device_list = new ArrayList<>();
    
    public void addDevice(Device device) {
        device_list.add(device);
        System.out.println(device.getName() + " just got registered to the SmartHomeController!");
    }

    public void removeDevice(Device device) {
        device_list.removeIf(dev -> dev.getName() == device.getName());
        System.out.println(device.getName() + " just got removed from the SmartHomeController...");
    }

    public void printDeviceList() {
        device_list.stream().forEach(dev -> System.out.println(dev.getName() + " - " + dev.getClass()));
    }

    public Boolean isIn(String deviceName) {
        return device_list.stream().anyMatch(dev -> dev.getName().equals(deviceName));
    }

    public void triggerEvent(Event event) {}


    public void update(Event event) {}
}
