package com.service;

import com.service.application.StadiumStateBuilder;
import com.service.entity.StadiumState;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceApplication {

	public static void main(String[] args) {
		StadiumState stadiumState = StadiumStateBuilder.getInitialStadiumState();

		SpringApplication.run(ServiceApplication.class, args);
	}

}
