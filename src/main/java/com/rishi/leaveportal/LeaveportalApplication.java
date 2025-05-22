package com.rishi.leaveportal;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition
@SpringBootApplication
public class LeaveportalApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaveportalApplication.class, args);
	}

}
