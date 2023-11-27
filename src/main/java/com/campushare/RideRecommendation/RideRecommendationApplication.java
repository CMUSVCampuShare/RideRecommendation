package com.campushare.RideRecommendation;

import com.campushare.RideRecommendation.kafka.KafkaUserEventProducer;
import com.campushare.RideRecommendation.services.RecommendationService;
import jakarta.el.BeanNameResolver;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RideRecommendationApplication {
	public static void main(String[] args) {
		System.out.println("Starting RideRecommendationApplication...");
		ConfigurableApplicationContext context = SpringApplication.run(RideRecommendationApplication.class, args);

		KafkaUserEventProducer producer = new KafkaUserEventProducer();

		for (int i = 1; i <= 10; i++) {
			String userId = "user" + i;
			String zipcode = "1234" + i; // Changed from address to zipcode
			String entryTime = (9 + i % 3) + ":00";
			String exitTime = (17 + i % 3) + ":00";

			String createUserJson = String.format(
					"{\"id\":\"%s\",\"zipcode\":\"%s\",\"schedule\":{\"entryTime\":\"%s\",\"exitTime\":\"%s\"}}",
					userId, zipcode, entryTime, exitTime);

			producer.sendCreateUserEvent(createUserJson);
		}

		producer.close();

		RecommendationService recommendationService = context.getBean(RecommendationService.class);
		recommendationService.processMockUserEventAndSaveRecommendations();
	}
}
