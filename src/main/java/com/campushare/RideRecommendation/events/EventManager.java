package com.campushare.RideRecommendation.events;

import com.campushare.RideRecommendation.events.data.EventData;
import com.campushare.RideRecommendation.events.listeners.EventListener;
import com.campushare.RideRecommendation.utils.EventType;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
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
        System.out.println("Subscribing to " + eventType + " event.");
        System.out.println("EventListeners: " + eventListeners);
        if (eventListeners != null) {
            for (EventListener listener : eventListeners) {
                System.out.println("EventListeners: " + eventListeners);
                System.out.println("EventData: " + data);
                listener.update(data);
            }
        }
    }
}
