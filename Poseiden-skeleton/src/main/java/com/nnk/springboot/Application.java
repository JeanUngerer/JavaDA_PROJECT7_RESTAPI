package com.nnk.springboot;

import com.nnk.springboot.auth.RsaKeyProperties;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.dtos.UserDTO;
import com.nnk.springboot.repositories.UserRepository;
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
	CommandLineRunner commandLineRunner(UserService users, UserRepository userRepository) {
		return args -> {
			users.createUser(new UserDTO(0, "testuser", "testpassword", "testFullname", "USER"));

			users.createUser(new UserDTO(0, "testadmin", "testpassword", "testFullname", "ADMIN"));

			userRepository.save(new User(3, "Administrator", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "admin", "ADMIN"));

			userRepository.save(new User(4, "User", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "user", "USER"));

		};
	}
}
