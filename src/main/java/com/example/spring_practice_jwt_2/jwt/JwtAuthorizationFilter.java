package com.example.spring_practice_jwt_2.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.spring_practice_jwt_2.auth.PrincipalDetails;
import com.example.spring_practice_jwt_2.model.User;
import com.example.spring_practice_jwt_2.repository.UserRepository;
import java.io.IOException;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.function.Supplier;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private UserRepository userRepository;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("인증이나 권한이 필요한 주소 요청이 됨");

		String authorizationInHeader = request.getHeader("Authorization");
		// 헤더에 Authorization이 없을 때
		if (authorizationInHeader == null) {
			System.out.println("헤더에 Authorization이 없을 때");
			chain.doFilter(request, response);
			return ;
		}

		StringTokenizer authorizationTokenizer = new StringTokenizer(request.getHeader("Authorization"));
		// Authorization 키는 있지만 내용이 없을 때
		if (!authorizationTokenizer.hasMoreTokens()) {
			System.out.println("Authorization 키는 있지만 내용이 없을 때");
			chain.doFilter(request, response);
			return ;
		}
		String tokenType = authorizationTokenizer.nextToken();
		// 토큰 타입이 Bearer가 아닐 때
		if (!tokenType.equals("Bearer")) {
			System.out.println("토큰 타입이 Bearer가 아닐 때");
			chain.doFilter(request, response);
			return ;
		}
		// Bearer 뒤에 아무것도 없을 때
		if (!authorizationTokenizer.hasMoreTokens()) {
			System.out.println("Bearer 뒤에 아무것도 없을 때");
			chain.doFilter(request, response);
			return ;
		}
		String jwtToken = authorizationTokenizer.nextToken();

		String username = JWT
				.require(Algorithm.HMAC512("cos")).build()
				.verify(jwtToken)
				.getClaim("username").asString();
		// 서명이 정상적으로 된 경우
		if (username != null) {
			Optional<User> optionalUser = userRepository.findByUsername(username);
			if (optionalUser.isEmpty()) {
				System.out.println("user not found");
				chain.doFilter(request, response);
				return ;
			}
			User user = optionalUser.get();
			PrincipalDetails principalDetails = new PrincipalDetails(user);
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					principalDetails, null, principalDetails.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);

			chain.doFilter(request, response);
		}



	}
}
