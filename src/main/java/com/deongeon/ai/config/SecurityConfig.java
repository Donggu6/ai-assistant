package com.deongeon.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.deongeon.ai.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtFilter;

	public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
		this.jwtFilter = jwtFilter;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http,
											JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
		http
		.csrf(csrf -> csrf.disable()) // <- CSRF 끔 (API 서버니까)
		.authorizeHttpRequests(auth -> auth
				.requestMatchers(
						"/",
						"/api/**",
						"/api/auth/**" // ← 회원가입 / 로그인 허용
				).permitAll()
				.anyRequest().authenticated()
				)
				.addFilterBefore(jwtAuthenticationFilter,
						org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
		
		
		return http.build();
		
	
	}
}