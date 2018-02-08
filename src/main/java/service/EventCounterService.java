package service;

import event.Event;

public interface EventCounterService {

    void onEvent(Event e);

    int getEventNumberPerMinute();

    int getEventNumberPerHour();

    int getEventNumberPerDay();
}
