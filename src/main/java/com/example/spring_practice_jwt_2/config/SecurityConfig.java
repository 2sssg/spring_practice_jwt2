package com.example.spring_practice_jwt_2.config;

import com.example.spring_practice_jwt_2.filter.CustomFilter1;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final CorsFilter corsFilter;

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
				.addFilterBefore(new CustomFilter1(), HeaderWriterFilter.class);

		httpSecurity
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilter(corsFilter) // @CrossOrigin(인증 x) , 시큐리티 필터에 등록
				.formLogin().disable()
				.httpBasic().disable()
				.authorizeRequests()
					.antMatchers("/api/v1/user/**")
						.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
					.antMatchers("/api/v1/manager/**")
						.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
					.antMatchers("/api/v1/admin/**")
						.access("hasRole('ROLE_ADMIN')")
					.anyRequest()
						.permitAll()

		;

		return httpSecurity.build();
	}
}
