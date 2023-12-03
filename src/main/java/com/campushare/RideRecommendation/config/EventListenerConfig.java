package com.campushare.RideRecommendation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import com.campushare.RideRecommendation.events.EventManager;
import com.campushare.RideRecommendation.events.listeners.UserBehaviorListener;
import com.campushare.RideRecommendation.utils.EventType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class EventListenerConfig {

    private static final Logger logger = LoggerFactory.getLogger(EventListenerConfig.class);

    @Autowired
    private EventManager eventManager;

    @Autowired
    private UserBehaviorListener userBehaviorListener;

    @Bean
    public boolean subscribeListeners() {
        logger.info("Subscribing listeners...");
        eventManager.subscribe(EventType.USER_CREATED, userBehaviorListener);
        logger.info("Subscribed to USER_CREATED event.");
        eventManager.subscribe(EventType.USER_UPDATED, userBehaviorListener);
        logger.info("Subscribed to USER_UPDATED event.");

        return true;
    }
}
