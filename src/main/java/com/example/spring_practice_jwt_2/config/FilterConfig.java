package com.example.spring_practice_jwt_2.config;

import com.example.spring_practice_jwt_2.filter.CustomFilter1;
import com.example.spring_practice_jwt_2.filter.CustomFilter2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

//	@Bean
//	public FilterRegistrationBean<CustomFilter1> customFilterFilterRegistrationBean1() {
//		FilterRegistrationBean<CustomFilter1> bean = new FilterRegistrationBean<>(new CustomFilter1());
//		bean.addUrlPatterns("/*");
//		bean.setOrder(0);
//		return bean;
//	}

	@Bean
	public FilterRegistrationBean<CustomFilter2> customFilterFilterRegistrationBean2() {
		FilterRegistrationBean<CustomFilter2> bean = new FilterRegistrationBean<>(new CustomFilter2());
		bean.addUrlPatterns("/*");
		bean.setOrder(1);
		return bean;
	}
}
