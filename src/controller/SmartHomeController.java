package controller;

import devices.Device;
import events.Event;
import java.util.ArrayList;
import java.util.List;

public class SmartHomeController implements Observer {
    
    List<Device> device_list = new ArrayList<>();
    
    void addDevice(Device device) {
        device_list.add(device);
    };

    void removeDevice(Device device) {}
    void triggerEvent(Event event) {}
    public void update(Event event) {}
}
