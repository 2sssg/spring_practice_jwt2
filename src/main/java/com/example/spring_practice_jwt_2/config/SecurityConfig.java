package com.example.spring_practice_jwt_2.config;

import com.example.spring_practice_jwt_2.filter.CustomFilter1;
import com.example.spring_practice_jwt_2.jwt.JwtAuthenticationFilter;
import com.example.spring_practice_jwt_2.jwt.JwtAuthorizationFilter;
import com.example.spring_practice_jwt_2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final CorsConfig corsConfig;

	private final UserRepository userRepository;

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


//		httpSecurity.addFilterBefore(new CustomFilter1(), HeaderWriterFilter.class);
		httpSecurity
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.formLogin().disable()
				.httpBasic().disable()
				.apply(new MyCustomDsl())
				.and()
				.authorizeRequests(authroize ->
						authroize.antMatchers("/api/v1/user/**")
							.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
						.antMatchers("/api/v1/manager/**")
							.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
						.antMatchers("/api/v1/admin/**")
							.access("hasRole('ROLE_ADMIN')")
						.anyRequest().permitAll()
				)

		;

		return httpSecurity.build();
	}


	public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {

		@Override
		public void configure(HttpSecurity httpSecurity) throws Exception {

			AuthenticationManager authenticationManager = httpSecurity.getSharedObject(AuthenticationManager.class);
			httpSecurity
					.addFilter(corsConfig.corsFilter())
					.addFilter(new JwtAuthenticationFilter(authenticationManager))
					.addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));
		}
	}
}
