package com.example.spring_practice_jwt_2.config;

import com.example.spring_practice_jwt_2.common.AppProperties;
import com.example.spring_practice_jwt_2.model.Role;
import com.example.spring_practice_jwt_2.model.User;
import com.example.spring_practice_jwt_2.service.user.UserService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	public ApplicationRunner initializeApp() {
		return new ApplicationRunner(){

			@Autowired AppProperties appProperties;

			@Autowired UserService userService;

			@Override
			public void run(ApplicationArguments args) throws Exception {
				User user = User.builder()
						.username(appProperties.getUserUsername())
						.password(appProperties.getPassword())
						.roles(Set.of(Role.USER))
						.build();

				User manager = User.builder()
						.username(appProperties.getManagerUsername())
						.password(appProperties.getPassword())
						.roles(Set.of(Role.USER, Role.MANAGER))
						.build();

				User admin = User.builder()
						.username(appProperties.getAdminUsername())
						.password(appProperties.getPassword())
						.roles(Set.of(Role.USER, Role.MANAGER, Role.ADMIN))
						.build();

				userService.createUser(user);
				userService.createUser(manager);
				userService.createUser(admin);
			}
		};
	}
}
