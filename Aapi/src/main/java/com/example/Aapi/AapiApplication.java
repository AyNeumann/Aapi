package com.example.Aapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AapiApplication.class, args);
	}

}
