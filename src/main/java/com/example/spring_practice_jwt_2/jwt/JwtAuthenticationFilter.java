package com.example.spring_practice_jwt_2.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.spring_practice_jwt_2.auth.PrincipalDetails;
import com.example.spring_practice_jwt_2.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	// /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
	@SneakyThrows
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("JwtAuthenticationFilter : 로그인 시도중");

		ObjectMapper om = new ObjectMapper();
		User user = om.readValue(request.getInputStream(), User.class);
		System.out.println(user);

		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

		// principalDetailsService의 loadUserByUsername() 함수가 실행됨
		Authentication authentication = authenticationManager.authenticate(authenticationToken);

		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		System.out.println(principalDetails);

		System.out.println("===========================");
		return authentication;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

		String jwtToken = JWT.create()
						.withSubject("tokenSubject")
						.withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60 * 10)))
						.withClaim("id", principalDetails.getUser().getId())
						.withClaim("username", principalDetails.getUsername())
						.sign(Algorithm.HMAC512("cos"))
				;

		System.out.println("successfulAuthentication 실행");
		response.addHeader("Authorization", "Bearer " + jwtToken);
	}
}
