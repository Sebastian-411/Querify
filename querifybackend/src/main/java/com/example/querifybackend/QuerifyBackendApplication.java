package com.example.querifybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuerifyBackendApplication {

	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(3000);
		SpringApplication.run(QuerifyBackendApplication.class, args);
	}

}
