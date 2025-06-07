package controller;

import devices.ObservableDevice;

public interface Observer {
    void update(ObservableDevice dev);
}
