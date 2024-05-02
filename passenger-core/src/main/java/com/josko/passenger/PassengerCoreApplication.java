package com.josko.passenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = "com.josko.passenger")
@EntityScan("com.josko.passenger.**.entity")
@EnableJpaRepositories(basePackages = "com.josko.passenger.**.repository")
@EnableScheduling
public class PassengerCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(PassengerCoreApplication.class, args);
	}

}
