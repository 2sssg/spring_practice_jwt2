package com.example.spring_practice_jwt_2.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {

	@GetMapping("/home")
	public String home() {
		return "home";
	}

	@PostMapping("/token")
	public String token() {
		return "token";
	}

	@GetMapping("/api/v1/user")
	public String user() {
		return "user";
	}

	@GetMapping("/api/v1/manager")
	public String manager() {
		return "manager";
	}

	@GetMapping("/api/v1/admin")
	public String admin() {
		return "admin";
	}
}
