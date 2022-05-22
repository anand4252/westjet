package com.westjet.profilemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.westjet")
public class ProfileManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfileManagementApplication.class, args);
	}

}
