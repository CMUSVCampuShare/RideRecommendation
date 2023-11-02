package com.campushare.RideRecommendation;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.SpringApplication;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RideRecommendationApplication {
	public static void main(String[] args) {
		System.out.println("Starting RideRecommendationApplication...");
		SpringApplication.run(RideRecommendationApplication.class, args);
	}
}
