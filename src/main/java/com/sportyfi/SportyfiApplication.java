package com.sportyfi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SportyfiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportyfiApplication.class, args);
		System.out.println("Server running at port 8080");
	}

}
