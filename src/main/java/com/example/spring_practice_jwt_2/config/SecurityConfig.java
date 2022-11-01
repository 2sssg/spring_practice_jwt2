package com.example.spring_practice_jwt_2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web -> web
				.ignoring()
				.antMatchers("/favicon")
				.antMatchers("/h2-console/**")
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.csrf().disable();
		return httpSecurity.build();
	}
}
