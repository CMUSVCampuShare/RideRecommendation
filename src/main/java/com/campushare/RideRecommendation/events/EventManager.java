package com.campushare.RideRecommendation.events;

import com.campushare.RideRecommendation.events.data.EventData;
import com.campushare.RideRecommendation.events.listeners.EventListener;
import com.campushare.RideRecommendation.utils.EventType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
    private Map<EventType, List<EventListener>> listeners = new HashMap<>();

    public EventManager(EventType... operations) {
        for (EventType operation : operations) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }

    public void subscribe(EventType eventType, EventListener listener) {
        List<EventListener> eventListeners = listeners.get(eventType);
        if (eventListeners == null) {
            eventListeners = new ArrayList<>();
            listeners.put(eventType, eventListeners);
        }
        eventListeners.add(listener);
    }

    public void unsubscribe(EventType eventType, EventListener listener) {
        List<EventListener> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            eventListeners.remove(listener);
        }
    }

    public void notify(EventType eventType, EventData data) {
        List<EventListener> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            for (EventListener listener : eventListeners) {
                listener.update(data);
            }
        }
    }
}
