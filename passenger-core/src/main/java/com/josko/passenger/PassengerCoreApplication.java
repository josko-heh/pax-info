package com.josko.passenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan("com.josko.passenger.**.entity")
@EnableJpaRepositories(basePackages = "com.josko.passenger.**.repository")
public class PassengerCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(PassengerCoreApplication.class, args);
	}

}
