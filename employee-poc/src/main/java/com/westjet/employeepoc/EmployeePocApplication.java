package com.westjet.employeepoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.westjet.core", "com.westjet.employeepoc"})
public class EmployeePocApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeePocApplication.class, args);
	}

}
