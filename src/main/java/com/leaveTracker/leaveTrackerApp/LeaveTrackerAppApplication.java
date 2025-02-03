package com.leaveTracker.leaveTrackerApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication(scanBasePackages = {"controllers", "services", "model", "repository"})
@EntityScan(basePackages = "model")  // Add this to scan JPA entities
@EnableJpaRepositories(basePackages = "repository")

public class LeaveTrackerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaveTrackerAppApplication.class, args);
	}

}
