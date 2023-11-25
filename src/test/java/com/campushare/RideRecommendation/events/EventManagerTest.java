package com.campushare.RideRecommendation.events;

import com.campushare.RideRecommendation.events.data.EventData;
import com.campushare.RideRecommendation.events.listeners.EventListener;
import com.campushare.RideRecommendation.utils.EventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class EventManagerTest {

    private EventManager eventManager;

    @Mock
    private EventListener listener1;

    @Mock
    private EventListener listener2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eventManager = new EventManager(EventType.USER_CREATED, EventType.USER_UPDATED);
    }

    @Test
    void whenSubscribe_thenListenerAdded() {
        eventManager.subscribe(EventType.USER_CREATED, listener1);

        // Manually trigger the notify to see if listener1 is called
        eventManager.notify(EventType.USER_CREATED, new EventData());

        verify(listener1).update(any());
        verify(listener2, never()).update(any());
    }

    @Test
    void whenUnsubscribe_thenListenerRemoved() {
        eventManager.subscribe(EventType.USER_CREATED, listener1);
        eventManager.unsubscribe(EventType.USER_CREATED, listener1);

        // Manually trigger the notify to see if listener1 is not called
        eventManager.notify(EventType.USER_CREATED, new EventData());

        verify(listener1, never()).update(any());
    }

    @Test
    void whenNotifyMultipleListeners_thenAllNotified() {
        eventManager.subscribe(EventType.USER_UPDATED, listener1);
        eventManager.subscribe(EventType.USER_UPDATED, listener2);

        // Manually trigger the notify to see if both listeners are called
        eventManager.notify(EventType.USER_UPDATED, new EventData());

        verify(listener1).update(any());
        verify(listener2).update(any());
    }
}
