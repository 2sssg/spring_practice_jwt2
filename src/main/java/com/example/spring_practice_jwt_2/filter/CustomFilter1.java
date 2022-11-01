package com.example.spring_practice_jwt_2.filter;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomFilter1 implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		System.out.println("filter 1");

		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

		// 토큰 : tempToken
		if (httpServletRequest.getMethod().equals("POST")) {
			String headerAuth = httpServletRequest.getHeader("Authorization");
			if (headerAuth.equals("tempToken")) {
				filterChain.doFilter(httpServletRequest, httpServletResponse);
			} else {
				PrintWriter outPrintWriter = httpServletResponse.getWriter();
				outPrintWriter.println("token value is Illegal");
				outPrintWriter.println("Authorize fail");
			}
		}
	}
}
