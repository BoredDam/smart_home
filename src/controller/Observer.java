package controller;

import events.Event;

public interface Observer {
    void update(Event event);
}
