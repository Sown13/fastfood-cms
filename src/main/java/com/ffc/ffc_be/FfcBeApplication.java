package com.ffc.ffc_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FfcBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FfcBeApplication.class, args);
	}

}
