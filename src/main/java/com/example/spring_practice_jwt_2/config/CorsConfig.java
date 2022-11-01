package com.example.spring_practice_jwt_2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		// 내 서버가 응답을 할 때 json을 자바스크립트에서 처리할 수 있게 할지를 설정하는 것
		corsConfiguration.setAllowCredentials(true);
		// 모든 ip에 응답을 허용하겠다
		corsConfiguration.addAllowedOrigin("*");
		// 모든 header에 응답을 허용하겠다
		corsConfiguration.addAllowedHeader("*");
		// get post put delete 모두 허용하겠다
		corsConfiguration.addAllowedMethod("*");
		source.registerCorsConfiguration("/api/**", corsConfiguration);
		return new CorsFilter(source);
	}
}
