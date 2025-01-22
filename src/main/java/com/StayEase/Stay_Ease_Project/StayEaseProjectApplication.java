package com.StayEase.Stay_Ease_Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.StayEase.Stay_Ease_Project.Repository")
@EntityScan(basePackages = "com.StayEase.Stay_Ease_Project.Model")
public class StayEaseProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(StayEaseProjectApplication.class, args);
	}

}
