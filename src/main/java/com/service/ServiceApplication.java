package com.service;

import com.service.application.StadiumStateBuilder;
import com.service.entity.StadiumState;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceApplication {
	public static StadiumState stadiumState = null;

	public static void main(String[] args) {
		stadiumState = StadiumStateBuilder.getInitialStadiumState();

		SpringApplication.run(ServiceApplication.class, args);
	}

}
