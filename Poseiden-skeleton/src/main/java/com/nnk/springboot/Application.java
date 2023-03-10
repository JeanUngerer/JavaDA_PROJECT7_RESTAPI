package com.nnk.springboot;

import com.nnk.springboot.auth.RsaKeyProperties;
import com.nnk.springboot.dtos.UserDTO;
import com.nnk.springboot.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserService users, PasswordEncoder encoder) {
		return args -> {
			users.createUser(new UserDTO(0, "testuser", "testpassword", "testFullname", "ROLE_USER"));

		};
	}
}
