package com.example.spring_practice_jwt_2.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "my-app")
@Getter @Setter
public class AppProperties {
	private String userUsername;
	private String managerUsername;
	private String adminUsername;
	private String password;
}
